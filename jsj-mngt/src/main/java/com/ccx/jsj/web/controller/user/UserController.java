package com.ccx.jsj.web.controller.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ccx.jsj.commons.conts.AppletConstants;
import com.ccx.jsj.commons.util.JwtUtils;
import com.ccx.jsj.model.domain.MenuDO;
import com.ccx.jsj.model.domain.RoleDO;
import com.ccx.jsj.model.domain.SuperEntity;
import com.ccx.jsj.model.domain.UserDO;
import com.ccx.jsj.web.param.dto.UserDTO;
import com.ccx.jsj.services.UserService;
import com.ccx.jsj.util.R;
import com.ccx.jsj.web.param.UserQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

import static com.ccx.jsj.emun.ResultEnum.InvalidTokenResult;


@Api("用户管理")
@RestController
@RequestMapping("user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    /**
     * 将HttpServletRequest注入，spring实现了ThreadLocal线程绑定的
     * 异步的不能从RequestContextHolder获取request，获取到的也是null
     */
    private final HttpServletRequest request;

    @Autowired
    @Qualifier("domainUserDetailService")
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation("刷新token")
    @PostMapping("refreshToken")
    public R refreshToken(){
        String authorization = request.getHeader(AppletConstants.TOKEN_HEADER);//从request中获取token
        String token = authorization.replace(AppletConstants.BEARER, "");
        if(StringUtils.isEmpty(token)){
            //无效的token
            return R.error(InvalidTokenResult.getCode(),InvalidTokenResult.getMsg());
        }
        boolean tokenExpired = JwtUtils.isTokenExpired(token);
        if(tokenExpired){
            //已过期
            //无效的token
            return R.error(InvalidTokenResult.getCode(),InvalidTokenResult.getMsg());
        }

        String username = JwtUtils.getUsernameByToken(token);
        // todo 需要增加openid查询
        UserDO userEntity = userService.queryUserByUsername(username);
        Set<GrantedAuthority> authorities=new HashSet<>();
        if(userEntity.getRoleSet()!=null && !userEntity.getRoleSet().isEmpty()){
            for (RoleDO role : userEntity.getRoleSet()) {
                for (MenuDO menuEntity : role.getMenuSet()) {
                    authorities.add(new SimpleGrantedAuthority(menuEntity.getPermission()));
                }
            }
        }

        String newToken = JwtUtils.createToken(username,authorities);
        return R.ok().put("token",newToken);
    }

    @ApiOperation("测试@RequestHeader注解")
    @PostMapping("test_head_token")
    public R getHeadToken(@RequestHeader("Authorization")String Authorization){
        return R.ok(Authorization);
    }

    @PostMapping("pageList")
    public R queryUserPageList(@RequestBody UserQuery userQuery){
        userQuery.setWrap();
        IPage<UserDO> page1 = userService.page(userQuery.getIPage(), userQuery.getWrapper());
        return R.ok(page1);
    }

    /**
     * 测试分组验证参数
     * @Validated 可以用在类型、方法和方法参数上。但是不能用在成员属性（字段）上。提供了分组功能
     * @Valid 可以用在方法、构造函数、方法参数和成员属性（字段）上。嵌套检验的时候用这个
     * @param dto
     * @return
     */
    @RequestMapping(value = "updateUser",method = RequestMethod.POST)
    public R update(@RequestBody @Validated(SuperEntity.Update.class)UserDTO dto){

        System.out.println(dto);
        return R.ok(dto);
    }


}
