package com.xu.wxxplatformserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.wxxplatformserver.pojo.SysLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author 旭日
 * @since 2021-12-24
 */
public interface ISysLogService extends IService<SysLog> {
    /**
     * 保存日志数据
     * @param browser 浏览器
     * @param ip 请求IP
     * @param os 操作系统
     * @param point
     * @param log 日志实体
     */
    @Async
    void save(String browser, String ip, String os, ProceedingJoinPoint point, SysLog log);
}
