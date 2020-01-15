package com.ccx.jsj;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccx.jsj.model.domain.UserDO;
import com.ccx.jsj.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDOServiceTest {

    @Autowired
    private UserService userService;

    public void testInsert(){

    }

    @Test
    public void testSelect(){
        String name="admin";
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.eq(!StringUtils.isEmpty(name),"username",name);
        List<UserDO> list = userService.list(wrapper);
        list.forEach(System.err::println);
    }

    @Test
    public void testPageList(){
        Page<UserDO> page = new Page<>(1, 3);
        IPage<UserDO> page1 = userService.page(page);
        System.out.println(page1.toString());
    }

    /**
     * 测试自定义sql分页
     */
    @Test
    public void testMyPageList(){
        Page<UserDO> page = new Page<>(1, 2);
        IPage<UserDO> ipage = userService.queryUserNameLike(page, "公");
        for (UserDO record : ipage.getRecords()) {
            System.out.println(record.toString());
        }
        System.out.println("================");
        List<UserDO> 公 = userService.queryUserNameLike("公");
        for (UserDO userDO : 公) {
            System.out.println(userDO);
        }
    }

    @Test
    public void testOr(){
        QueryWrapper<UserDO> userDOQueryWrapper = new QueryWrapper<>();
        userDOQueryWrapper.like("username","公")
                .or()
                .like("mobile","182")
                .or()
                .isNotNull("email");
        List<UserDO> list = userService.list(userDOQueryWrapper);
        for (UserDO userDO : list) {
            System.out.println(userDO);
        }
    }

    @Test
    public void testSelectOne(){

        OUT:
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.println(i*j);
                if(i*j>50){
                    break OUT;
                }
            }
        }

    }

}
