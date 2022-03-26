package me.xu.modules.system.service.dto;

import lombok.Data;
import me.xu.annotation.Query;

/**
 * Description 系统用户查询参数
 * Date 2022/3/25 17:11
 * Version 1.0.1
 *
 * @author Wen
 */
@Data
public class UserQueryParam {

    @Query
    private Long userId;

    @Query
    private Integer gender;

    @Query(blurry = "username")
    private String blurry;

}
