package cn.ltl.sell.controller;

import cn.ltl.sell.DTO.OrderDTO;
import cn.ltl.sell.VO.ResultVO;
import cn.ltl.sell.converter.OrderForm2OrderDTOConverter;
import cn.ltl.sell.enums.ResultEnum;
import cn.ltl.sell.exception.SellException;
import cn.ltl.sell.form.OrderForm;
import cn.ltl.sell.service.BuyerService;
import cn.ltl.sell.service.OrderService;
import cn.ltl.sell.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerService buyerService;

    @PostMapping("/create")
    public ResultVO create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确，orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO);
        Map<String, Object> data = new HashMap<>();
        data.put("orderId", createResult.getOrderId());
        return ResultVOUtil.success(data);
    }

    @GetMapping("/list")
    public ResultVO list(String openid, @RequestParam(defaultValue = "0") Integer page,@RequestParam(defaultValue = "10") Integer size) {
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, new PageRequest(page, size));
        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    @GetMapping("/detail")
    public ResultVO detail(String openid, String orderId) {
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)) {
            log.error("【订单详情】参数不正确");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }

    @PostMapping("/cancel")
    public ResultVO cancel(String openid, String orderId) {
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)) {
            log.error("【取消订单】参数不正确");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}
