package me.xu.exception;

/**
 * Description 全局异常处理
 * Date 2021/11/28 21:39
 * Version 1.0.1
 *
 * @author Wen
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.xu.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;

@ControllerAdvice
public class GlobalException {

    @ResponseBody
    @ExceptionHandler
    public Result handlerException(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 不同异常返回不同状态码
        Result result = null;
        // 如果是未登录异常
        if (e instanceof NotLoginException) {
            NotLoginException ee = (NotLoginException) e;
            result = Result.unauthorized();
        }
        // 如果是角色异常
        else if(e instanceof NotRoleException) {
            NotRoleException ee = (NotRoleException) e;
            result = Result.forbidden();
        }
        // 如果是权限异常
        else if(e instanceof NotPermissionException) {
            NotPermissionException ee = (NotPermissionException) e;
            result = Result.forbidden();
        }
        //// 如果是被封禁异常 后续实现
        //else if(e instanceof DisableLoginException) {
        //    DisableLoginException ee = (DisableLoginException) e;
        //}
        // 普通异常, 输出：500 + 异常信息
        else {
            result = Result.failed(e.getMessage());
        }
        // 返回给前端
        return result;
    }
}
