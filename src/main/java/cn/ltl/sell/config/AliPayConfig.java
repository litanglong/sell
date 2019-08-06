package cn.ltl.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("alipay")
public class AliPayConfig {
    private String appId;
    private String appPrivateKey;
    private String aliPayPublicKey;
    private String charset;
    private String returnUrl;
    private String notifyUrl;
}
