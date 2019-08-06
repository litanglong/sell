package cn.ltl.sell.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AliConfig {

    @Autowired
    private AliPayConfig aliPayConfig;

    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(),
                "json",
                aliPayConfig.getCharset(),
                aliPayConfig.getAliPayPublicKey(),
                "RSA2");
    }
}
