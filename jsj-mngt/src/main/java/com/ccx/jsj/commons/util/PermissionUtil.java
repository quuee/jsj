package com.ccx.jsj.commons.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * 自定义接口权限判断工具 @PreAuthorize(@pms.hasPermission('xxx'))、@PreAuthorize("@pms.hasPermission('sys_user_set')")
 * PreAuthorize注解根据返回的true false 判断
 */

@Component("pms")
public class PermissionUtil {

	/**
	 * 判断接口是否有权限
	 *
	 * @param permission 权限
	 * @return {boolean}
	 */
	public boolean hasPermission(String permission) {
		if (StringUtils.isEmpty(permission)) {
			return false;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return false;
		}
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority authority : authorities) {
            String authorityPermission = authority.getAuthority();
            if(StringUtils.isEmpty(authorityPermission)){
                return false;
            }
            if(authorityPermission.equals(permission)){
                return true;
            }
        }
        return false;
	}
}
