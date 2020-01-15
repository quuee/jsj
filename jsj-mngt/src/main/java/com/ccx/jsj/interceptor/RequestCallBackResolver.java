//package com.ccx.jsj.interceptor;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.MethodParameter;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.ModelAndViewContainer;
//import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;
//
//
///**
// * 入参的回调解析，实体实现RequestCallBack接口，如果参数增加了@RequestBody注解，就不会走这里。
// * 一般是用不到
// */
//public class RequestCallBackResolver implements HandlerMethodArgumentResolver {
//
//    private static final Logger logger = LoggerFactory.getLogger(RequestCallBackResolver.class);
//
//    private HandlerMethodArgumentResolver resolver = new ServletModelAttributeMethodProcessor(true);
//
//    /**
//     * 如果添加了@RequestBody注解，会走RequestResponseBodyMethodProcessor
//     * 用于判定是否需要处理该参数分解，返回true为需要，并会去调用下面的方法resolveArgument
//     * @param methodParameter
//     * @return
//     */
//    @Override
//    public boolean supportsParameter(MethodParameter methodParameter) {
//        //isAssignableFrom()方法是从类继承的角度去判断，instanceof关键字是从实例继承的角度去判断
//        //isAssignableFrom()方法是判断是否为某个类的父类，instanceof关键字是判断是否某个类的子类
//        //RequestCallbcak 这个很像，但不是这个
//        boolean assignableFrom = RequestCallBack.class.isAssignableFrom(methodParameter.getParameterType());
//        logger.info("supportsParameter:{}",assignableFrom);
//        logger.info("ParameterType:{}",methodParameter.getParameterType());
//        return assignableFrom;
//    }
//
//    @Override
//    public Object resolveArgument(MethodParameter methodParameter,
//                                  ModelAndViewContainer modelAndViewContainer,
//                                  NativeWebRequest nativeWebRequest,
//                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
//        RequestCallBack requestCallback = (RequestCallBack)resolver.resolveArgument(methodParameter,
//                modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
//
//        if (requestCallback != null) {
//            requestCallback.paramCallback();
//        }
//        return requestCallback;
//
//    }
//
//}
