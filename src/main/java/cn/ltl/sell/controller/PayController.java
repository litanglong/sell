package cn.ltl.sell.controller;

import cn.ltl.sell.DTO.OrderDTO;
import cn.ltl.sell.enums.ResultEnum;
import cn.ltl.sell.exception.SellException;
import cn.ltl.sell.service.OrderService;
import cn.ltl.sell.service.PayService;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;

    @RequestMapping("/create")
    public String create(String orderId, String returnUrl, Model model) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        AlipayTradeWapPayResponse alipayTradeWapPayResponse = payService.create(orderDTO);
        model.addAttribute("orderDTO", orderDTO);
        model.addAttribute("url", alipayTradeWapPayResponse.getBody());
        return "pay/create";
    }

    @PostMapping("/notify")
    public String notifyMsg(Map map) {


        return "success";
    }

    @PostMapping("/returnUrl")
    public String returnUrl(Map map) {

        return "success";
    }

}
