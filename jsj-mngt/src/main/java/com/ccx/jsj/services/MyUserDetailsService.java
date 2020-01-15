package com.ccx.jsj.services;


import com.ccx.jsj.model.domain.MenuDO;
import com.ccx.jsj.model.domain.RoleDO;
import com.ccx.jsj.model.domain.UserDO;
import com.ccx.jsj.model.vo.MyUserDetails;
import com.ccx.jsj.validate.my.MyLoginAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component("myUserDetailService")
public class MyUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String var1) throws UsernameNotFoundException {

        UserDO userEntity = null;

        String[] parameter = var1.split(":");

        //手机号码
        if (MyLoginAuthenticationFilter.SPRING_SECURITY_RESTFUL_TYPE_PHONE.equals(parameter[0])) {
            userEntity = userService.queryUserByPhone(parameter[1]);
            if (userEntity == null) {
                logger.error("找不到该用户，手机号码：{}", parameter[1]);
                throw new UsernameNotFoundException("找不到该用户，手机号码：" + parameter[1]);
            }

        }
        //扫码
        else if (MyLoginAuthenticationFilter.SPRING_SECURITY_RESTFUL_TYPE_QR.equals(parameter[0])) {

            userEntity = null;

        }
        // 微信code
        else if (MyLoginAuthenticationFilter.SPRING_SECURITY_RESTFUL_TYPE_WECHAT.equals(parameter[0])) {
            // TODO 微信登录
            String code = parameter[1];
            System.out.println(code);
            //根据code 获取openId，根据openId获取用户信息

        }
        // 用户名密码
        else {

            //用户名密码登录
            userEntity = userService.queryUserByUsername(parameter[1]);
            if (userEntity == null) {
                logger.error("找不到该用户，用户名：{}", parameter[1]);
                throw new UsernameNotFoundException("找不到该用户，用户名：" + parameter[1]);
            }
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        if (userEntity.getRoleSet() != null && !userEntity.getRoleSet().isEmpty()) {
            for (RoleDO role : userEntity.getRoleSet()) {
                for (MenuDO menuEntity : role.getMenuSet()) {
                    authorities.add(new SimpleGrantedAuthority(menuEntity.getPermission()));
                }
            }
        }

        // 返回带有用户权限信息的User
        return new MyUserDetails(
                userEntity.getUsername(),
                userEntity.getPassword(),
                authorities,
                userEntity.getUserId(),
                userEntity.getSuperUser());
    }
}
