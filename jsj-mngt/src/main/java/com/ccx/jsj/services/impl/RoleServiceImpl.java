package com.ccx.jsj.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccx.jsj.dao.RoleMapper;
import com.ccx.jsj.model.domain.RoleDO;
import com.ccx.jsj.services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleDO> implements RoleService {
}
