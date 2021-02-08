package com.wjh.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {    //日志处理，使用AOP

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.wjh.blog.controller.*.*(..))")   //说明是切面，规定切面拦截哪些类   public 包名.所有类.所有方法（..）  (任何参数的方法)
    public void log() {}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {   //JointPoint:获取拦截类里类名、方法名
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();    //获取HttpServletRequest，以获取类中的url ip
        String url = request.getRequestURL().toString();       //获取类中的url
        String ip = request.getRemoteAddr();                   //获取类中的ip

        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();    //类名+.+方法名
        Object[] args = joinPoint.getArgs();                   //获取请求的参数
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);    //--------构造方法
        logger.info("Request : {}", requestLog);
    }

    @After("log()")
    public void doAfter() {
        logger.info("--------doAfter--------");
    }


    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterRuturn(Object result) {
        logger.info("Result : {}", result);
        logger.info("--------doAfterReturning--------");
    }

    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
