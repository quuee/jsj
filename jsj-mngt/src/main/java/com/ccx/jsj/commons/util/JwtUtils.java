package com.ccx.jsj.commons.util;

import com.ccx.jsj.commons.conts.AppletConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class JwtUtils {

    /**
     * 生成jwt token
     * @param principal
     * @param grantedAuthorities
     * @return
     */
    public static String createToken(String principal,Collection<? extends GrantedAuthority> grantedAuthorities) {

        String token = Jwts.builder()
                //声明一些东西
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //加密方式
                .signWith(SignatureAlgorithm.HS256, AppletConstants.APPLET_SECRET)
                //添加了权限的内容
                .claim(AppletConstants.AUTHORITIES, grantedAuthorities)
                //签发时间
                .setIssuedAt(new Date())
                //签发人
                .setIssuer("ccx")
                //jwt主体，即它的所有人
                .setSubject(principal)
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + AppletConstants.EXPIRATION_DATE * 1000))
                .compact();
        return token;
    }

    /**
     * 判断过期
     * @param token
     * @return
     */
    public static boolean isTokenExpired(String token) {
        Date expiredDate = getTokenBody(token).getExpiration();
        return expiredDate.before(new Date());
    }

    /**
     * 获取用户名
     * @param token
     * @return
     */
    public static String getUsernameByToken(String token) {
        return getTokenBody(token).getSubject();
    }

    /**
     * 获取用户权限
     */
    public static Set<GrantedAuthority> getUserAuthortiesByToken(String token) {
        Claims tokenBody = getTokenBody(token);
        Set<GrantedAuthority> userAuthortiessByClaims = getUserAuthortiesByClaims(tokenBody);
        return userAuthortiessByClaims;
    }

    /**
     * 获取用户权限
     * @param tokenBody
     * @return
     */
    public static Set<GrantedAuthority> getUserAuthortiesByClaims(Claims tokenBody) {
        List<LinkedHashMap<String,Object>> authorities = (List<LinkedHashMap<String,Object>>)tokenBody.get(AppletConstants.AUTHORITIES);

        Set<GrantedAuthority> authoritieSet=new HashSet<>();
        for (LinkedHashMap<String, Object> authority : authorities) {
            for (Map.Entry<String, Object> stringObjectEntry : authority.entrySet()) {
                authoritieSet.add(new SimpleGrantedAuthority((String)stringObjectEntry.getValue()));
            }
        }

        return authoritieSet;
    }

    public static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(AppletConstants.APPLET_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 根据过期时间，当前时间，限定时间（分钟），判断是否需要重新生成jwt token
     *
     * @param expiredDate
     * @param minutes
     * @return
     */
    public static Boolean isNeedRefreshToken(Date expiredDate, Integer minutes) {
        Date now = new Date();
        //判断是否过期
        boolean after = expiredDate.after(now);
        if (!after) {
            //如果过期时间在当前时间之前，返回false
            return false;
        }

        //判断过期时间 距离 当前时间是否在限定时间内
        //如果不是就可以刷新token，返回true
        if (minutes == null || minutes == 0) {
            minutes = 30;//分钟
        }
        now.setTime(now.getTime() + minutes * 60 * 1000);
        boolean after1 = expiredDate.after(now);
        return !after1;

    }
}
