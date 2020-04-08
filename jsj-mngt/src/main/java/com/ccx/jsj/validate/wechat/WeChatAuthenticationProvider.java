package com.ccx.jsj.validate.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author ccx
 * @version V1.0
 * @Package com.ccx.jsj.validate.wechat
 * @date 2020/4/8 22:00
 */
@Component
public class WeChatAuthenticationProvider implements AuthenticationProvider {

    private static final String weixin_url="https://api.weixin.qq.com/sns/jscode2session?appid={1}&secret={2}&js_code={3}&grant_type=authorization_code";

    @Autowired
    private RestTemplate restTemplate;

    //注入微信小程序 userDetailService

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WeChatAuthenticationToken weChatAuthenticationToken=
                (WeChatAuthenticationToken)authentication;

        Object o = restTemplate.getForObject(weixin_url, Object.class, "", "", weChatAuthenticationToken.getCredentials());
        JSONObject jsonObject = JSON.parseObject((String) o);
        Object errcode = jsonObject.get("errcode");
        Object openid = jsonObject.get("openid");

        //根据openid查询本系统中是否有该用户

        //如果没有该用户 ，则注册 ，并返回token

        //如果有，则返回token

        return new WeChatAuthenticationToken((String)openid,null);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return WeChatAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
