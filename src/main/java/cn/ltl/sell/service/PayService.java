package cn.ltl.sell.service;

import cn.ltl.sell.DTO.OrderDTO;
import com.alipay.api.response.AlipayTradeWapPayResponse;

public interface PayService {
    AlipayTradeWapPayResponse create(OrderDTO orderDTO);
}
