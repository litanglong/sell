package cn.ltl.sell.converter;

import cn.ltl.sell.DTO.OrderDTO;
import cn.ltl.sell.dataobject.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMaster) {
        List<OrderDTO> orderDTOList = orderMaster.stream().map(
                e -> convert(e)
        ).collect(Collectors.toList());
        return orderDTOList;
    }
}
