package com.ccx.jsj.web.param;

import com.ccx.jsj.commons.util.AbsWrapper;
import com.ccx.jsj.model.domain.UserDO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class UserQuery extends AbsWrapper<UserDO> {

    private String name;

    private String mobile;


    @Override
    public void setWrap() {
        like("username",name);
        eq("mobile",mobile);
    }
}
