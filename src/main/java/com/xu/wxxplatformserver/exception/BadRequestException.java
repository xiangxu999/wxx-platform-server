package com.xu.wxxplatformserver.exception;

/**
 * Description 统一异常处理
 * Date 2021/12/23 14:07
 * Version 1.0.1
 *
 * @author Wen
 */
public class BadRequestException extends RuntimeException{

    public BadRequestException(String msg){
        super(msg);
    }

}
