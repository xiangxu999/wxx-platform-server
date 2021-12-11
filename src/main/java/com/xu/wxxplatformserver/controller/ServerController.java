package com.xu.wxxplatformserver.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.xu.wxxplatformserver.common.Result;
import com.xu.wxxplatformserver.pojo.Server;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description 服务器监控Controller
 * Date 2021/12/10 10:55
 * Version 1.0.1
 *
 * @author Wen
 */
@Api(tags = "服务端监控")
@RestController
public class ServerController {

    @ApiOperation(value = "获取服务端信息")
    @RequestMapping(value = "/monitor/server", method = RequestMethod.GET)
    @SaCheckPermission("system:monitor:server")
    public Result serverInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return Result.success(server);
    }
}
