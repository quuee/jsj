package com.ccx.jsj.services.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccx.jsj.dao.UserMapper;
import com.ccx.jsj.model.domain.UserDO;
import com.ccx.jsj.services.UserService;
import com.ccx.jsj.web.param.LoginParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Override
    public UserDO queryUserByPhone(String phone) {
        return baseMapper.selectUserByPhone(phone);
    }

    @Override
    public UserDO queryUserByUsername(String username) {
        return baseMapper.selectUserByName(username);
    }

    @Override
    public IPage<UserDO> queryUserNameLike(Page<UserDO> page, String name){
        return baseMapper.selectUserNameLike(page, name);
    }

    @Override
    public List<UserDO> queryUserNameLike(String name) {
        return baseMapper.selectUserNameLike(name);
    }
}
