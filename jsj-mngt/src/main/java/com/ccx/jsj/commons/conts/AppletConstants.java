package com.ccx.jsj.commons.conts;

public interface AppletConstants {

    long EXPIRATION_DATE = 7200;//过期时间

    String APPLET_SECRET="123456";

    long APPLET_FORBID_REFRES_DATE=7200000;//过期刷新时间

    String TOKEN_HEADER="Authorization";
    String BEARER="Bearer ";
    String AUTHORITIES="authorities";
    String NEED_REFRESH="need-refresh";//是否需要刷新token标记
}
