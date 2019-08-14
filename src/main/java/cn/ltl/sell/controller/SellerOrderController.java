package cn.ltl.sell.controller;

import cn.ltl.sell.DTO.OrderDTO;
import cn.ltl.sell.exception.SellException;
import cn.ltl.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("list")
    public String list(Model model, @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {
        Page<OrderDTO> orderDTOPage = orderService.findList(new PageRequest(page-1, size));
        model.addAttribute("orderDTOPage", orderDTOPage);
        model.addAttribute("currentPage", page);
        return "order/list";
    }

    @GetMapping("{orderId}")
    public String detail(Model model, @PathVariable String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        model.addAttribute("orderDTO", orderDTO);
        return "order/detail";
    }

    @GetMapping("cancel")
    public String cancel(Model model, String orderId) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("【卖家端】取消订单异常，{}", e.getMessage());
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("url", "sell/seller/order/list");
        }
        return "redirect:list";
    }

    @GetMapping("finish")
    public String finish(Model model, String orderId) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (SellException e) {
            log.error("【卖家端】完结订单异常，{}", e.getMessage());
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("url", "sell/seller/order/list");
        }
        return "redirect:list";
    }

    @GetMapping("index")
    public String index() {
        return "index";
    }
}
