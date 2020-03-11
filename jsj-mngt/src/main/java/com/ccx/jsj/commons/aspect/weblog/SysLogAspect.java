package com.ccx.jsj.commons.aspect.weblog;

import com.alibaba.fastjson.JSON;
import com.ccx.jsj.commons.util.IPUtil;
import com.ccx.jsj.util.StackTraceUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


/**
 * 系统日志，切面处理类
 */
@Aspect
@Component
public class SysLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);


    @Pointcut("@annotation(com.ccx.jsj.commons.aspect.weblog.SysLog)")
    public void annotationLogPointCut() {

    }

    /**
        整个表达式可以分为五个部分：

         1、execution(): 表达式主体。

         2、第一个*号：表示返回类型，*号表示所有的类型。

         3、包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，com.sample.service.impl包、子孙包下所有类的方法。

         4、第二个*号：表示类名，*号表示所有的类。

         5、*(..):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数。
    */
    @Pointcut("execution(public * com.ccx.jsj.web.controller..*.*(..))")
    public void packageLogPointCut() {

    }

    @Pointcut("annotationLogPointCut() || packageLogPointCut()")
    public void bizPoint(){

    }

    @Around("bizPoint()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object proceed;
        try {
            proceed = point.proceed();
        }catch (Exception e){
            String trace = StackTraceUtil.getTrace(e);
            logger.error(e.getMessage()+": {}",trace);
            throw e;
        }
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveSysLog(point, time, proceed);

        return proceed;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time,Object proceed) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLog syslog = method.getAnnotation(SysLog.class);
        String operate=syslog!=null?syslog.operation():"";


        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();


        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args);

        //返回的参数
        String returnParam = JSON.toJSONString(proceed);

        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = IPUtil.getIp(request);

//        JSONObject principal = MySecurityUtil.getPrincipal();
//        Object username = principal.get("username");

        logger.info("名字：{}，操作：{}，请求类名：{}，请求方法：{}，参数：{}，返回：{}，ip：{}，耗时：{}",
                "",operate,className,methodName,params,returnParam,ip,time);
    }


}
