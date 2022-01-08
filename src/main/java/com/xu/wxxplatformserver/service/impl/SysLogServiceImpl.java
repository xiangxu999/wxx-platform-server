package com.xu.wxxplatformserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.wxxplatformserver.annotation.Log;
import com.xu.wxxplatformserver.common.Result;
import com.xu.wxxplatformserver.mapper.SysLogMapper;
import com.xu.wxxplatformserver.pojo.SysLog;
import com.xu.wxxplatformserver.service.ISysLogService;
import com.xu.wxxplatformserver.utils.LogUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author 旭日
 * @since 2021-12-24
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Autowired
    private SysLogMapper logMapper;

    @Override
    public void save(String browser, String ip, String os, ProceedingJoinPoint point, SysLog log) {
        // 提前构建好这个日志的描述
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Log aopLog = method.getAnnotation(Log.class);

        // 方法路径
        String methodName = point.getTarget().getClass().getName() + "." + signature.getName() + "()";

        // 描述
        if (log != null) {
            log.setDescription(aopLog.value());
        }

        assert log != null;
        log.setRequestIp(ip);
        log.setAddress(LogUtil.getCityInfo(ip));
        log.setMethod(methodName);
        log.setUsername(LogUtil.getUsername(method, point.getArgs()));
        log.setParams(LogUtil.getParameter(method, point.getArgs()));
        log.setBrowser(browser);
        log.setPlatform(os);
        if (log.getLogId() == null) {
            save(log);
        } else {
            updateById(log);
        }
    }

    @Override
    public Result queryAll(String type, Integer current, Integer size) {
        Page<SysLog> page = new Page<>(current, size);
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<SysLog>().eq("log_type", type);
        Page<SysLog> result = logMapper.selectPage(page, queryWrapper);
        if (result.getRecords().size() > 0) {
            return Result.success(result);
        } else {
            return Result.failed();
        }
    }

    @Override
    public Result deleteAll(String type) {
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<SysLog>().eq("log_type", type);
        logMapper.delete(queryWrapper);
        return Result.success();
    }
}
