package com.ccx.jsj.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccx.jsj.dao.MenuMapper;
import com.ccx.jsj.model.domain.MenuDO;
import com.ccx.jsj.services.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuDO> implements MenuService {
}
