package cn.ltl.sell.controller;

import cn.ltl.sell.dataobject.SellerInfo;
import cn.ltl.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @GetMapping("/login")
    public String login() {
        return "page-login";
    }

    @PostMapping("/login")
    public String login(@Valid SellerInfo sellerInfo, BindingResult bindingResult , Model model, HttpSession session) {
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
        session.setAttribute("loginUser", loginUser);
        return "redirect:seller/order/list";
    }

    @GetMapping("/logOut")
    public String logOut(HttpSession session) {
        session.removeAttribute("loginUser");
        return "page-login";
    }
}
