package com.ccx.jsj.validate.jwt;

import com.ccx.jsj.commons.conts.AppletConstants;
import com.ccx.jsj.commons.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 过滤器处理所有HTTP请求，并检查是否存在带有正确令牌的Authorization标头。例如，如果令牌未过期或签名密钥正确。
 */
public class TokenAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthorizationFilter.class);

    public TokenAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String authorization = request.getHeader(AppletConstants.TOKEN_HEADER);
        // 如果请求头中没有token信息则直接放行了
        if (authorization == null || !authorization.startsWith(AppletConstants.BEARER)) {
            chain.doFilter(request, response);
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置授权信息
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(authorization));

        String token = authorization.replace(AppletConstants.BEARER, "");
        Date expiration = JwtUtils.getTokenBody(token).getExpiration();
        //判断是否需要刷新token,30分钟内的需要
        Boolean needRefreshToken = JwtUtils.isNeedRefreshToken(expiration, 30);
        response.addHeader(AppletConstants.NEED_REFRESH,needRefreshToken.toString());

        super.doFilterInternal(request, response, chain);
    }

    /**
     * 获取用户认证信息 Authentication
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String authorization) {
        String token = authorization.replace(AppletConstants.BEARER, "");
        try {
            Claims tokenBody = JwtUtils.getTokenBody(token);
            logger.info("tokenBody:{}",tokenBody);
            Set<GrantedAuthority> userAuthortiesByClaims = JwtUtils.getUserAuthortiesByClaims(tokenBody);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(tokenBody.getSubject(),
                    null, userAuthortiesByClaims);
//            usernamePasswordAuthenticationToken.setDetails(userMap);
            return usernamePasswordAuthenticationToken;

        } catch (SignatureException | ExpiredJwtException | MalformedJwtException | IllegalArgumentException exception) {
            logger.warn("Request to parse JWT with invalid signature . Detail : {}" , exception.getMessage());
        }
        return null;
    }
}
