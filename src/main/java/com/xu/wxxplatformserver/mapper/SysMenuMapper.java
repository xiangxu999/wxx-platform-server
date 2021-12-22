package com.xu.wxxplatformserver.mapper;

import com.xu.wxxplatformserver.pojo.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 旭日
 * @since 2021-11-08
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户id获得所拥有的菜单Id列表
     * @param userId 用户id
     * @return 菜单Id列表
     */
    @Select("SELECT * from sys_menu where sys_menu.menu_id in (select DISTINCT sys_role_menu.menu_id FROM sys_user_role LEFT JOIN sys_role_menu ON sys_user_role.role_id = sys_role_menu.role_id WHERE sys_user_role.user_id = #{userId})")
    List<SysMenu> getSysMenuIdsByUserId(Long userId);
}
