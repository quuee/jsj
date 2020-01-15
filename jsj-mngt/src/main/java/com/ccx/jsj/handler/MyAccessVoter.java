//package com.ccx.jsj.handler;
//
//import com.ccx.jsj.model.vo.MyUserDetails;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.access.AccessDecisionVoter;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.FilterInvocation;
//
//import java.util.Collection;
//
////@Component
//public class MyAccessVoter implements AccessDecisionVoter<Object> {
//
//    private static final Logger logger = LoggerFactory.getLogger(MyAccessVoter.class);
//
//    @Value("${hidden.url}")
//    private String urlHiddenString;
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
//    @Override
//    public int vote(Authentication authentication, Object o, Collection<ConfigAttribute> collection) {
//
//        // 隐藏字符串
//        FilterInvocation filterInvocation = (FilterInvocation) o;
//        String hiddenParameter = filterInvocation.getHttpRequest().getParameter(urlHiddenString);
//        if (hiddenParameter != null){
//            //如果是隐藏字符串，直接通过
//            return ACCESS_GRANTED;
//        }
//
//        // 没有权限默认拒绝
//        if (authentication == null){
//            return ACCESS_DENIED;
//        }
//
//        // 匿名用户 不允许
//        if (authentication instanceof AnonymousAuthenticationToken) {
//            return ACCESS_DENIED;
//        }
//
//        MyUserDetails principal = (MyUserDetails)authentication.getPrincipal();
//        if(principal.getSuperUser()!=null && principal.getSuperUser()){
//            //如果是超级用户，直接通过
//            return ACCESS_GRANTED;
//        }
//        logger.info("urlHiddenString:{}",urlHiddenString);
//        return ACCESS_ABSTAIN;
//    }
//}
