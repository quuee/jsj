package com.ccx.jsj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccx.jsj.model.domain.RoleDO;

import java.util.List;

public interface RoleMapper extends BaseMapper<RoleDO> {

    List<RoleDO> selectRoleByUserId(Integer userId);
}
