package me.xu.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import me.xu.common.Result;
import me.xu.service.impl.SysLogServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author 旭日
 * @since 2021-12-24
 */
@Api(tags = "系统日志")
@RestController
@RequestMapping("/system/log")
public class SysLogController {

    @Autowired
    private SysLogServiceImpl sysLogService;

    @ApiOperation(value = "日志列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @SaCheckPermission("system:monitor:log")
    public Result List(@RequestParam(value = "type") String type,
                       @RequestParam(value = "current", defaultValue = "1") Integer current,
                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return sysLogService.queryAll(type, current, size);
    }

    @ApiOperation(value = "日志删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @SaCheckPermission("system:monitor:log")
    public Result delete(@RequestParam(value = "type") String type) {
        return sysLogService.deleteAll(type);
    }

}
