package com.ccx.jsj.validate.my;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MyAuthenticationToken extends AbstractAuthenticationToken {


    private static final long serialVersionUID = 110L;
    private final Object principal;
    private Object credentials;
    private String type;
    private String mobile;
    private String wechatCode;

    /**
     * This constructor can be safely used by any code that wishes to create a
     * <code>UsernamePasswordAuthenticationToken</code>, as the {@link
     * #isAuthenticated()} will return <code>false</code>.
     * @param principal 用户名
     * @param credentials 密码
     * @param type 登录类型
     * @param mobile 手机
     */
    public MyAuthenticationToken(Object principal, Object credentials,String type, String mobile) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.type = type;
        this.mobile = mobile;
        this.setAuthenticated(false);
    }

    /**
     * This constructor should only be used by <code>AuthenticationManager</code> or <code>AuthenticationProvider</code>
     * implementations that are satisfied with producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
     * token token.
     *
     * @param principal
     * @param credentials
     * @param authorities
     */
    public MyAuthenticationToken(Object principal, Object credentials,String type, String mobile, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.type = type;
        this.mobile = mobile;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public String getType() {
        return this.type;
    }

    public String getMobile() {
        return this.mobile;
    }

    public String getWechatCode() {
        return this.wechatCode;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if(isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
