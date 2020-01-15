package com.ccx.jsj.web.controller;

import com.ccx.jsj.util.R;
import com.ccx.jsj.web.param.UserQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("测试")
@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

//    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @ApiOperation("测试")
    @PostMapping("user")
    @PreAuthorize("@pms.hasPermission('sys_user_set')")
    public R test1(UserQuery userQuery){
        log.info("参数：{}",userQuery);

        return R.ok(userQuery);
    }

    @ApiOperation("测试")
    @PostMapping("dev")
    @PreAuthorize("@pms.hasPermission('sys_user_add')")
    public R devtools(Integer number){

        R ok = R.ok(number + 1);
        return ok;
    }
}
