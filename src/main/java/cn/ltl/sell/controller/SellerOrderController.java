package cn.ltl.sell.controller;

import cn.ltl.sell.DTO.OrderDTO;
import cn.ltl.sell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
