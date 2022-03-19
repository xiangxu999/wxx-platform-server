package me.xu.mapper;

import me.xu.pojo.SysUser;
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
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据菜单id获得对应的用户
     * @param menuId 菜单id
     * @return 用户
     */
    @Select("SELECT DISTINCT sys_user.* FROM sys_user LEFT JOIN sys_user_role ON sys_user.user_id = sys_user_role.user_id LEFT JOIN sys_role_menu ON sys_user_role.role_id = sys_role_menu.role_id WHERE sys_role_menu.menu_id = #{menuId};")
    List<SysUser> getSysUserListByMenuId(Long menuId);
}
