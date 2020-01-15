package com.ccx.jsj.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class MyUserDetails extends User {

    private Integer userId;

    private Boolean superUser;

    private Set<MenuRouteButtonVO> menuRouteButtonVOSet;

    public MyUserDetails(String username,
                         String password,
                         Collection<? extends GrantedAuthority> authorities,
                         Integer userId,
                         Boolean superUser) {
        super(username, password, authorities);
        this.userId = userId;
        this.superUser=superUser;
    }

    public MyUserDetails(String username,
                         String password,
                         Collection<? extends GrantedAuthority> authorities,
                         Set<MenuRouteButtonVO> menuRouteButtonVOSet,
                         Integer userId,
                         Boolean superUser) {
        super(username, password, authorities);
        this.userId = userId;
        this.superUser=superUser;
        this.menuRouteButtonVOSet=menuRouteButtonVOSet;
    }

    public MyUserDetails(String username,
                         Collection<? extends GrantedAuthority> authorities,
                         Integer userId,
                         Boolean superUser) {
        super(username, null,authorities);
        this.userId = userId;
        this.superUser=superUser;
    }
}
