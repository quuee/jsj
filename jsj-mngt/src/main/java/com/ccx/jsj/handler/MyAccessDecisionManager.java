//package com.ccx.jsj.handler;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.AccessDecisionManager;
//import org.springframework.security.access.AccessDecisionVoter;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.vote.AuthenticatedVoter;
//import org.springframework.security.access.vote.RoleVoter;
//import org.springframework.security.access.vote.UnanimousBased;
//import org.springframework.security.authentication.InsufficientAuthenticationException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.access.expression.WebExpressionVoter;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
///**
// * @Description: Decision决定的意思。
// * MyAccessDecisionManager 自定义权限决策管理器
// **/
//@Component
//public class MyAccessDecisionManager implements AccessDecisionManager {
//
//    @Autowired
//    private MyAccessVoter myAccessVoter;
//
//    @Override
//    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
//
//    }
//
//    @Override
//    public boolean supports(ConfigAttribute configAttribute) {
//        return true;
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return true;
//    }
//
//}
