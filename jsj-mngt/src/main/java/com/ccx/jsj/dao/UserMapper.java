package com.ccx.jsj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccx.jsj.model.domain.UserDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMapper extends BaseMapper<UserDO> {

    UserDO selectUserByPhone(@Param("phone") String phone);

    UserDO selectUserByName(@Param("username")String username);

    /**
     * 测试自定义分页
     * @return
     */
    IPage<UserDO> selectUserNameLike(Page<UserDO> page, @Param("name") String name);
    List<UserDO> selectUserNameLike(@Param("name") String name);

}
