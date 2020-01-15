package com.ccx.jsj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccx.jsj.model.domain.MenuDO;

import java.util.List;

public interface MenuMapper extends BaseMapper<MenuDO> {

    List<MenuDO> selectMenuByRoleId(Integer roleId);
}
