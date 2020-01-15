package com.ccx.jsj;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccx.jsj.dao.UserMapper;
import com.ccx.jsj.model.domain.UserDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDOMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect(){
        List<UserDO> userDOS = userMapper.selectList(null);
        userDOS.forEach(
                System.out::println
        );
    }


    @Test
    public void testSelectPage(){
        Page<UserDO> userPage = new Page<>();
        IPage<UserDO> userIPage = userMapper.selectPage(userPage, null);
        userIPage.getRecords().forEach(System.out::println);
    }

    @Test
    public void testInsert(){
        UserDO userDO = new UserDO();
        userDO.setUsername("公孙锦");
        userDO.setPassword("123456");
        userDO.setMobile("123456");
        userDO.setEnable(true);
        userDO.setSource(1);
        userMapper.insert(userDO);
    }

    @Test
    public void testSelectById(){
        Integer id=1;
        Assert.notNull(id, "id not to null");
        UserDO userDO = userMapper.selectById(id);
        System.out.println(userDO.toString());
    }
}
