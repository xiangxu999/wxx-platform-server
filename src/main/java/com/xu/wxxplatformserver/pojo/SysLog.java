package com.xu.wxxplatformserver.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author 旭日
 * @since 2021-12-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="系统日志", description="系统日志")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    @ApiModelProperty(value = "日志描述")
    private String description;

    @ApiModelProperty(value = "日志类型")
    private String logType;

    @ApiModelProperty(value = "方法")
    private String method;

    @ApiModelProperty(value = "参数")
    private String params;

    @ApiModelProperty(value = "请求ip")
    private String requestIp;

    @ApiModelProperty(value = "请求耗时")
    private Long time;

    @ApiModelProperty(value = "请求用户名")
    private String username;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "游览器")
    private String browser;

    @ApiModelProperty(value = "异常详情")
    private byte[] exceptionDetail;

    @ApiModelProperty(value = "平台")
    private String platform;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    public SysLog(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }


}
