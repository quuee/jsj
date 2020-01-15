package com.ccx.jsj.services;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccx.jsj.model.domain.UserDO;
import com.ccx.jsj.web.param.LoginParam;

import java.util.List;

public interface UserService extends IService<UserDO> {

    UserDO queryUserByPhone(String phone);

    UserDO queryUserByUsername(String username);

    /**
     * 测试自定义分页
     * @param page
     * @param name
     * @return
     */
    IPage<UserDO> queryUserNameLike(Page<UserDO> page, String name);

    List<UserDO> queryUserNameLike(String name);

}
