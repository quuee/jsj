package com.ccx.jsj.validate.my;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
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
 * 自定义登陆filter，新增登陆方式：验证码、二维码扫码、账号密码；
 * 验证码登陆：
 * POST: /myLogin?type=phone&phone=13000000000&verifyCode=1000
 * 二维码登陆：（没做）
 * POST: /myLogin?type=qr&qrCode=token
 * 账号密码登陆：
 * POST: /myLogin?username=username&password=password
 * 微信登录:
 * POST: /myLogin?type=wechat&wechatCode=123456
 *
 * client_id client_secret 需要传
 * 此filter 为生成自定义的 MyAuthenticationToken
 */
public class MyLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(MyLoginAuthenticationFilter.class);

    public static final String SPRING_SECURITY_RESTFUL_TYPE_PHONE = "phone";//手机 验证码登录
    public static final String SPRING_SECURITY_RESTFUL_TYPE_QR = "qr"; // 扫码登录 （没做）
    public static final String SPRING_SECURITY_RESTFUL_TYPE_DEFAULT = "user"; //默认 用户名密码登录
    public static final String SPRING_SECURITY_RESTFUL_TYPE_WECHAT = "wechat";//微信登录

    // 登陆类型：user:用户密码登陆；phone:手机验证码登陆；qr:二维码扫码登陆；wechat:微信登录
    private static final String SPRING_SECURITY_RESTFUL_TYPE_KEY = "type";
    // 登陆终端：1：移动端登陆，包括微信公众号、小程序等；0：PC后台登陆
    private static final String SPRING_SECURITY_RESTFUL_MOBILE_KEY = "mobile";
    private static final String SPRING_SECURITY_RESTFUL_WECHAT_KEY = "wechatCode";

    private static final String SPRING_SECURITY_RESTFUL_USERNAME_KEY = "username";
    private static final String SPRING_SECURITY_RESTFUL_PASSWORD_KEY = "password";
    private static final String SPRING_SECURITY_RESTFUL_PHONE_KEY = "phone";
    private static final String SPRING_SECURITY_RESTFUL_VERIFY_CODE_KEY = "verifyCode";
    private static final String SPRING_SECURITY_RESTFUL_QR_CODE_KEY = "qrCode";

    public static final String SPRING_SECURITY_RESTFUL_LOGIN_URL = "/myLogin";
    private static final String SPRING_SECURITY_RESTFUL_REQUEST_METHOD = "POST";

    private boolean postOnly = true;


    public MyLoginAuthenticationFilter() {
        super(new AntPathRequestMatcher(SPRING_SECURITY_RESTFUL_LOGIN_URL, SPRING_SECURITY_RESTFUL_REQUEST_METHOD));
        logger.info("SPRING_SECURITY_RESTFUL_LOGIN_URL:{},METHOD:{}", SPRING_SECURITY_RESTFUL_LOGIN_URL, SPRING_SECURITY_RESTFUL_REQUEST_METHOD);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !request.getMethod().equals(SPRING_SECURITY_RESTFUL_REQUEST_METHOD)) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        //并且符合表单形式 form-data 或 application/x-www-form-urlencoded
//        if (postOnly && !request.getContentType().equalsIgnoreCase("application/x-www-form-urlencoded")) {
//            throw new AuthenticationServiceException(
//                    "Authentication contentType not supported: " + request.getContentType());
//        }

        // 登陆类型：user:用户密码登陆；phone:手机验证码登陆；qr:二维码扫码登陆
        String type = obtainParameter(request, SPRING_SECURITY_RESTFUL_TYPE_KEY);
        String mobile = obtainParameter(request, SPRING_SECURITY_RESTFUL_MOBILE_KEY);
        MyAuthenticationToken authRequest;
        String principal;
        String credentials = "";

        // 手机验证码登陆
        if (SPRING_SECURITY_RESTFUL_TYPE_PHONE.equals(type)) {
            principal = obtainParameter(request, SPRING_SECURITY_RESTFUL_PHONE_KEY);
            credentials = obtainParameter(request, SPRING_SECURITY_RESTFUL_VERIFY_CODE_KEY);
        }
        // 二维码扫码登陆
        else if (SPRING_SECURITY_RESTFUL_TYPE_QR.equals(type)) {
            principal = obtainParameter(request, SPRING_SECURITY_RESTFUL_QR_CODE_KEY);
            credentials = null;
        }
        //微信code登录
        else if (SPRING_SECURITY_RESTFUL_TYPE_WECHAT.equalsIgnoreCase(type)) {
            //将code 存到 principal
            principal = obtainParameter(request, SPRING_SECURITY_RESTFUL_WECHAT_KEY);
        }
        // 账号密码登陆
        else {
            principal = obtainParameter(request, SPRING_SECURITY_RESTFUL_USERNAME_KEY);
            credentials = obtainParameter(request, SPRING_SECURITY_RESTFUL_PASSWORD_KEY);
            if (StringUtils.isEmpty(type))
                type = SPRING_SECURITY_RESTFUL_TYPE_DEFAULT;
        }

        principal = principal.trim();
        authRequest = new MyAuthenticationToken(
                principal, credentials, type, mobile);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void setDetails(HttpServletRequest request,
                            AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    private String obtainParameter(HttpServletRequest request, String parameter) {
        String result = request.getParameter(parameter);
        return result == null ? "" : result;
    }

}
