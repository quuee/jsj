package com.ccx.jsj.web.param.dto;

import com.ccx.jsj.model.domain.BaseEntity;
import com.ccx.jsj.model.domain.RoleDO;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseEntity {

    private String username;

    private String password;

    private String mobile;

    private Boolean enable;//是否激活 1激活 0 未激活

    private Integer source;

    private Boolean superUser;

    private Set<RoleDO> roleSet;
}
