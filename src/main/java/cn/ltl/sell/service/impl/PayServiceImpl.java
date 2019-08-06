package cn.ltl.sell.service.impl;

import cn.ltl.sell.DTO.OrderDTO;
import cn.ltl.sell.service.PayService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private AlipayClient alipayClient;

    @Override
    public AlipayTradeWapPayResponse create(OrderDTO orderDTO) {
         //获得初始化的AlipayClient
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://fr2pvi.natappfree.cc/sell/pay/returnUrl");
        alipayRequest.setNotifyUrl("http://fr2pvi.natappfree.cc/sell/pay/notify");//在公共参数中设置回跳和通知地址
        //填充业务参数
        AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
        model.setOutTradeNo(orderDTO.getOrderId());
        model.setTotalAmount(orderDTO.getOrderAmount().toString());
        model.setProductCode("QUICK_WAP_PAY");
        alipayRequest.setBizModel(model);
        AlipayTradeWapPayResponse alipayTradeWapPayResponse = null;
        try {
            alipayTradeWapPayResponse = alipayClient.pageExecute(alipayRequest, "get");//调用SDK生成表单
        } catch (AlipayApiException e) {
            log.error("【支付宝】调用支付出错", e);
        }
        return alipayTradeWapPayResponse;
    }
}
