package com.ccx.jsj.validate.my;

import com.ccx.jsj.services.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationProvider extends MyAbstractAuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationProvider.class);

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean supports(Class<?> authentication) {
        return MyAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, Authentication authentication) throws AuthenticationException {
//        Object salt = null;
//        if(this.saltSource != null) {
//            salt = this.saltSource.getSalt(userDetails);
//        }

        if(authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("MyAbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            MyAuthenticationToken authentication2=(MyAuthenticationToken)authentication;
            String presentedPassword = authentication2.getCredentials().toString();

            // 验证开始
            if(MyLoginAuthenticationFilter.SPRING_SECURITY_RESTFUL_TYPE_PHONE.equals(authentication2.getType())){
                // 手机验证码验证，调用公共服务查询后台验证码缓存： key 为authentication.getPrincipal()的value， 并判断其与验证码是否匹配,
                Object principal = authentication2.getPrincipal();
                String veriyCode = redisTemplate.opsForValue().get(principal);

                if(null==veriyCode || !veriyCode.equals(presentedPassword)){
                    logger.debug("Authentication failed: verifyCode does not match stored value");
                    throw new BadCredentialsException(this.messages.getMessage("MyAbstractUserDetailsAuthenticationProvider.badCredentials", "Bad verifyCode"));
                }

            }

            if(MyLoginAuthenticationFilter.SPRING_SECURITY_RESTFUL_TYPE_QR.equals(authentication2.getType())){
                // 二维码只需要根据 qrCode 查询到用户即可，所以此处无需验证

            }

            if(MyLoginAuthenticationFilter.SPRING_SECURITY_RESTFUL_TYPE_DEFAULT.equals(authentication2.getType())){

                // 用户名密码验证
                if(!this.passwordEncoder.matches(presentedPassword,userDetails.getPassword())) {
                    logger.debug("Authentication failed: password does not match stored value");
                    throw new BadCredentialsException(this.messages.getMessage("MyAbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
                }
            }

            if(MyLoginAuthenticationFilter.SPRING_SECURITY_RESTFUL_TYPE_WECHAT.equals(authentication2.getType())){
                //微信登录后，查询出用户名密码
                // 用户名密码验证
                if(!this.passwordEncoder.matches(presentedPassword,userDetails.getPassword())) {
                    logger.debug("Authentication failed: password does not match stored value");
                    throw new BadCredentialsException(this.messages.getMessage("MyAbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
                }
            }
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, MyAuthenticationToken authentication) throws AuthenticationException {
        UserDetails loadedUser;
        try {
            // 调用loadUserByUsername时加入type前缀
            loadedUser = myUserDetailsService.loadUserByUsername(authentication.getType() + ":" + username);
        } catch (UsernameNotFoundException var6) {
            throw var6;
        } catch (Exception var7) {
            throw new InternalAuthenticationServiceException(var7.getMessage(), var7);
        }

        if(loadedUser == null) {
            throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
        } else {
            return loadedUser;
        }
    }


}
