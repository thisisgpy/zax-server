package com.ganpengyu.zax.common;

import com.ganpengyu.zax.common.util.StopWatch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 接口返回切面
 *
 * @author Pengyu Gan
 * CreateDate 2025/3/11
 */
@Slf4j
@Aspect
@Component
public class ZaxAspect {

    @Pointcut("execution(public com.ganpengyu.zax.common.ZaxResult *(..))")
    public void execute() {

    }

    /**
     * 切面逻辑
     *
     * @param pjp {@link ProceedingJoinPoint} 切点上下文
     * @return {@link ZaxResult} 请求响应数据模型
     */
    @Around("execute()")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) {
        UserContext.setContext("admin");
        ZaxResult<?> result;
        StopWatch stopWatch = new StopWatch();
        try {
            result = (ZaxResult<?>) pjp.proceed();
            log.info("{} cost time {} ms", pjp.getSignature(), stopWatch.getElapse());
        } catch (Throwable e) { // 处理异常
            result = handlerException(pjp, e);
            log.info("[Execute Error] {} cost time {} ms", pjp.getSignature(), stopWatch.getElapse());
        } finally {
            UserContext.removeContext();
        }
        return result;
    }

    /**
     * 请求出现异常的处理控制
     *
     * @param pjp {@link ProceedingJoinPoint} 切点上下文
     * @param e   {@link Throwable} 异常信息
     * @return {@link ZaxResult} 请求响应数据模型
     */
    private ZaxResult<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        return ZaxResult.error(e.getMessage());
    }

}
