package com.ccx.jsj.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
@ToString
@EqualsAndHashCode(callSuper = true)
public class UserDO extends BaseEntity {

    @TableId(type=IdType.AUTO)
    private Integer userId;

    private String username;

    private String password;

    private String mobile;

    private Boolean enable;//是否激活 1激活 0 未激活

    private Integer source;

    private Boolean superUser;

    @TableField(exist = false)
    private Set<RoleDO> roleSet;
}
