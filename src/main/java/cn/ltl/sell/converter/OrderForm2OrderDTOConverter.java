package cn.ltl.sell.converter;

import cn.ltl.sell.DTO.OrderDTO;
import cn.ltl.sell.dataobject.OrderDetail;
import cn.ltl.sell.form.OrderForm;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        List<OrderDetail> orderDetailList = JSONObject.parseArray(orderForm.getItems(), OrderDetail.class);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
