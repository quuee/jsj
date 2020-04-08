package com.ccx.jsj.validate.wechat;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @author ccx
 * @version V1.0
 * @Package com.ccx.jsj.validate.wechat
 * @date 2020/4/8 21:57
 */
public class WeChatAuthenticationToken extends AbstractAuthenticationToken {

    private String openid;

    private String jsCode;

    public WeChatAuthenticationToken(String openid,String jsCode){
        super(null);
        this.openid=openid;
        this.jsCode=jsCode;
    }

    public WeChatAuthenticationToken(String jsCode){
        super(null);
        this.jsCode=jsCode;
    }


    @Override
    public Object getCredentials() {
        return this.jsCode;
    }

    @Override
    public Object getPrincipal() {
        return openid;
    }
}
