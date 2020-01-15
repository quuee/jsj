package com.ccx.jsj.model.vo;

import lombok.Data;

import java.util.Set;

@Data
public class MenuRouteButtonVO {

    private Integer menuId;

    private String menuName;

    private String permission;

    private String path;

    private Integer parentMenuId;

    private Integer menuType;

    private Set<MenuRouteButtonVO> menuRouteButtonVOSet;
}
