package cn.ltl.sell.config;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WechatMpConfig {

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpInMemoryConfigStorage());
        return wxMpService;
    }

    @Bean
    public WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage() {
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        config.setAppId(wechatAccountConfig.getAppID()); // 设置微信公众号的appid
        config.setSecret(wechatAccountConfig.getAppsecret()); // 设置微信公众号的app corpSecret
        return config;
    }
}
