package com.ccx.jsj.model.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("menu")
public class MenuDO {

    @TableId
    private Integer menuId;

    private String menuName;

    private String permission;

    private String path;

    private Integer parentMenuId;

    private Integer menuType;
}
