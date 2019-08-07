package cn.ltl.sell.DTO;

import cn.ltl.sell.dataobject.OrderDetail;
import cn.ltl.sell.enums.OrderStatusEnum;
import cn.ltl.sell.enums.PayStatusEnum;
import cn.ltl.sell.util.EnumUtil;
import cn.ltl.sell.util.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getMessage(payStatus, PayStatusEnum.class);
    }

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getMessage(orderStatus, OrderStatusEnum.class);
    }
}
