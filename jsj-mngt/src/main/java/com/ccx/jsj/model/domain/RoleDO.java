package com.ccx.jsj.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@TableName("role")
public class RoleDO {

    @TableId
    private Integer roleId;

    private String roleName;

    private Boolean enable;

    private Date createDate;

    @TableField(exist = false)
    private Set<MenuDO> menuSet;
}
