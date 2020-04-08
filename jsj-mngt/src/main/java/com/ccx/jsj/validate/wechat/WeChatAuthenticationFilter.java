package com.ccx.jsj.validate.wechat;

import com.ccx.jsj.validate.jwt.TokenAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 微信小程序登陆
 * @author ccx
 * @version V1.0
 * @Package com.ccx.jsj.validate.wechat
 * @date 2020/4/8 21:51
 */
public class WeChatAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(WeChatAuthenticationFilter.class);

    public static final String SPRING_SECURITY_RESTFUL_LOGIN_URL = "/wechat/login";

    public WeChatAuthenticationFilter(){
        super(new AntPathRequestMatcher(SPRING_SECURITY_RESTFUL_LOGIN_URL, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (!httpServletRequest.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + httpServletRequest.getMethod());
        }

        String jsCode = httpServletRequest.getParameter("js_code");
        if(StringUtils.isEmpty(jsCode)){
            throw new AuthenticationCredentialsNotFoundException("js_code 为空");
        }

        WeChatAuthenticationToken authenticationToken =
                new WeChatAuthenticationToken(jsCode);
        authenticationToken.setDetails(authenticationDetailsSource.buildDetails(httpServletRequest));

        return this.getAuthenticationManager().authenticate(authenticationToken);
    }
}
