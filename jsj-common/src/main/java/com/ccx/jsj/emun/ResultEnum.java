package com.ccx.jsj.emun;

public enum ResultEnum {

    SuccessResult(1,"请求成功"),

    LoginFailResult(401,"登录失败"),
    AccessDeniedResult(402,"无权限"),
    InvalidTokenResult(401,"无效的token"),

    ParamErrResult(510,"参数错误"),
    ExistResult(520,"已存在"),
    FailResult(530,"操作失败");

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
