package com.xu.wxxplatformserver.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description 邮箱前端控制器
 * Date 2021/12/16 9:43
 * Version 1.0.1
 *
 * @author Wen
 */
@Api(tags = "邮箱")
@RestController
@RequestMapping(value = "/system/mail")
public class MailController {

    //@Autowired
    //private MailService mailService;
    //
    ///**
    // * 发送普通邮件
    // * @param to 收件人地址
    // * @param subject 邮件主题
    // * @param content 邮件内容
    // * @return
    // */
    //@ApiOperation(value = "发送普通邮件")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(name = "to", value = "发送地址"),
    //        @ApiImplicitParam(name = "subject", value = "邮件主题"),
    //        @ApiImplicitParam(name = "content", value = "邮件内容")
    //})
    //@RequestMapping(value = "/simple", method = RequestMethod.POST)
    //public Result sendSimpleMail(@RequestParam(value = "to")  String to,
    //                             @RequestParam(value = "subject") String subject,
    //                             @RequestParam(value = "content") String content) {
    //    mailService.sendSimpleMail(to,subject,content);
    //    return Result.success("邮件发送成功");
    //}


}
