package com.xu.wxxplatformserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.wxxplatformserver.common.Result;
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
     *
     * @param browser 浏览器
     * @param ip      请求IP
     * @param os      操作系统
     * @param point   point
     * @param log     日志实体
     */
    @Async
    void save(String browser, String ip, String os, ProceedingJoinPoint point, SysLog log);

    /**
     * 根据日志类型分页查询
     *
     * @param type    日志类型
     * @param current 当前页数
     * @param size    每页大小
     * @return Result
     */
    Result queryAll(String type, Integer current, Integer size);

    /**
     * 根据日志类型清空日志
     *
     * @param type 日志类型
     * @return
     */
    Result deleteAll(String type);
}
