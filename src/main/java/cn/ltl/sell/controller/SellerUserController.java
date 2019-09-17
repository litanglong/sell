package cn.ltl.sell.controller;

import cn.ltl.sell.dataobject.SellerInfo;
import cn.ltl.sell.service.SellerService;
import cn.ltl.sell.service.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@Controller
public class SellerUserController {

    @Autowired
    private SellerService sellerService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/login")
    public String login() {
        return "page-login";
    }

    @PostMapping("/login")
    public String login(@Valid SellerInfo sellerInfo, BindingResult bindingResult , Model model, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("msg", bindingResult.getFieldError().getDefaultMessage());
            model.addAttribute("url","");
            return "error";
        }
        SellerInfo loginUser = sellerService.login(sellerInfo);
        if (loginUser == null) {
            model.addAttribute("msg", "登录失败");
            model.addAttribute("url","");
            return "error";
        }
        String token = UUID.randomUUID().toString();
        //保存token到redis
        redisTemplate.opsForValue().set("token_" + token, loginUser.getUsername(), 60*60*60);
        //保存token到cookie
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(60*60*60);
        response.addCookie(cookie);
        return "redirect:seller/order/list";
    }

    @GetMapping("/logOut")
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
        request.removeAttribute("loginUser");
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        for (Cookie c : cookies) {
            if (c.getName().equals("token")) {
                cookie = c;
            }
        }
        if (cookie != null) {
            redisTemplate.opsForValue().getOperations().delete("token_" + cookie.getValue());
            cookie.setMaxAge(0);
            cookie.setValue(null);
            response.addCookie(cookie);
        }
        return "page-login";
    }

}
