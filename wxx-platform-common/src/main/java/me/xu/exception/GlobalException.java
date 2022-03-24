package me.xu.exception;

/**
 * Description 全局异常处理
 * Date 2021/11/28 21:39
 * Version 1.0.1
 *
 * @author Wen
 */

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import me.xu.common.Result;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler
    public Result handlerException(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 不同异常返回不同状态码
        Result result = null;
        // 未登录异常
        if (e instanceof NotLoginException) {
            result = Result.unauthorized();
        }
        // 角色异常
        else if (e instanceof NotRoleException) {
            result = Result.forbidden();
        }
        // 权限异常
        else if (e instanceof NotPermissionException) {
            result = Result.forbidden();
        }
        //// 封禁异常 后续实现
        //else if(e instanceof DisableLoginException) {
        //    DisableLoginException ee = (DisableLoginException) e;
        //}
        // 参数校验失败
        else if (e instanceof MethodArgumentNotValidException) {
            result = Result.validateFailed(e.getMessage());
        }
        // 自定义异常
        else if (e instanceof ResultException) {
            ResultException ee = (ResultException) e;
            result = Result.failed(ee.getResultCode());
        }
        // 普通异常, 输出：500 + 异常信息
        else {
            result = Result.failed();
        }
        // 返回给前端
        return result;
    }
}
