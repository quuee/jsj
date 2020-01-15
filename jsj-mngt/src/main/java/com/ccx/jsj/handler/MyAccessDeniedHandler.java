//package com.ccx.jsj.handler;
//
//import com.alibaba.fastjson.JSONObject;
//import com.ccx.jsj.emun.ResultEnum;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.MediaType;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 无权限分为 1、登录无权限，2、未登录无权限
// *
// * jwt 被认为是匿名用户 ，异常就不交由MyAccessDeniedHandler处理，被直接抛出来。
// */
//public class MyAccessDeniedHandler implements AccessDeniedHandler {
//
//    private static final Logger logger = LoggerFactory.getLogger(MyAccessDeniedHandler.class);
//
//
//    @Override
//    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
//
//        logger.error("MyAccessDeniedHandler:{}","已拒绝");
//
//        Map<String, Object> resMap = new HashMap<>();
//        resMap.put("msg", "已拒绝或无权限");
//        resMap.put("code", ResultEnum.AccessDeniedResult.getCode());
//
//        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        httpServletResponse.getWriter().write(JSONObject.toJSONString(resMap));
////        httpServletResponse.flushBuffer();
//    }
//}
