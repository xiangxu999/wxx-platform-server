package me.xu.aspect;

import me.xu.pojo.SysLog;
import me.xu.service.impl.SysLogServiceImpl;
import me.xu.utils.LogUtil;
import me.xu.utils.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import me.xu.utils.ThrowableUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Description 切入点
 * Date 2021/12/24 16:39
 * Version 1.0.1
 *
 * @author Wen
 */
@Aspect
@Component
@Slf4j
public class AopLog {

    @Autowired
    private SysLogServiceImpl logService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    /***
     * 配置切入点
     */
    @Pointcut("@annotation(me.xu.annotation.Log)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param point join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint point) throws Throwable {
        Object result;
        // 设置开始时间
        currentTime.set(System.currentTimeMillis());
        result = point.proceed();
        // 配置一个简单log
        SysLog log = new SysLog("INFO", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.save(LogUtil.getBrowser(request), LogUtil.getIp(request), LogUtil.getPlatform(request), point, log);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param point join point for advice
     * @param e     exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint point, Throwable e) {
        SysLog log = new SysLog("ERROR", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        log.setExceptionDetail(ThrowableUtil.getStackTrace(e).getBytes());
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.save(LogUtil.getBrowser(request), LogUtil.getIp(request), LogUtil.getPlatform(request), (ProceedingJoinPoint) point, log);
    }


}
