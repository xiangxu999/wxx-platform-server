package com.xu.wxxplatformserver.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Description 定时任务
 * Date 2021/12/10 11:21
 * Version 1.0.1
 *
 * @author Wen
 */
@Slf4j
@Component
public class ServerTask {

    //@Autowired
    //private SimpMessagingTemplate wsTemplate;
    //
    ///**
    // * 按照标准时间来算，每隔 2s 执行一次
    // */
    //@Scheduled(cron = "0/2 * * * * ?")
    //public void websocket() throws Exception {
    //    log.info("【推送消息】开始执行：{}", DateUtil.formatDateTime(new Date()));
    //    // 查询服务器状态
    //    Server server = new Server();
    //    server.copyTo();
    //    wsTemplate.convertAndSend(WebSocketConst.PUSH_SERVER, JSONUtil.toJsonStr(server));
    //    log.info("【推送消息】执行结束：{}", DateUtil.formatDateTime(new Date()));
    //}
}
