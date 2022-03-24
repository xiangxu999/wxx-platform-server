package me.xu.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import me.xu.common.ResultCode;

/**
 * Description 统一异常处理
 * Date 2021/12/23 14:07
 * Version 1.0.1
 *
 * @author Wen
 */
@Getter
public class ResultException extends RuntimeException {

    @ApiModelProperty(value = "异常状态码")
    private Integer code;
    @ApiModelProperty(value = "消息")
    private String message;
    @ApiModelProperty(value = "枚举")
    private ResultCode resultCode;

    public ResultException() {
    }

    public ResultException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ResultException(String message) {
        this.message = message;
    }

    public ResultException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
        this.code = resultCode.getCode();
    }


}
