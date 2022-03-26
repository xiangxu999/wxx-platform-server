package me.xu.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xu.modules.system.pojo.SysUser;
import me.xu.modules.system.service.dto.UserQueryParam;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 旭日
 * @since 2021-11-08
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据分页和查询参数进行查询
     *
     * @param page       分页
     * @param queryParam 查询参数
     * @return 用户列表
     */
    Page<SysUser> userList(Page<SysUser> page, UserQueryParam queryParam);


    /**
     * 根据id获取用户的信息
     *
     * @param id 用户id
     * @return 用户
     */
    SysUser userInfo(Long id);

    /**
     * 用户新增
     *
     * @param sysUser 用户对象
     * @return Boolean
     */
    Boolean userSave(SysUser sysUser);


    /**
     * 用户修改
     *
     * @param sysUser 用户对象
     * @return Boolean
     */
    Boolean userUpdate(SysUser sysUser);


    /**
     * 用户删除
     *
     * @param ids 用户对象ids
     * @return Boolean
     */
    Boolean userDelete(Long[] ids);


    /**
     * 用户角色分配
     *
     * @param id      用户id
     * @param roleIds 角色ids
     * @return Boolean
     */
    Boolean roleChange(Long id, Long[] roleIds);

    /**
     * 用户重置密码
     *
     * @param id 用户id
     * @return Boolean
     */
    Boolean repass(Long id);


    /**
     * 返回一个账号所拥有的角色标识集合
     *
     * @param userId 用户id
     * @return 角色id集合
     */
    List<Long> getUserRoleList(Long userId);

    /**
     * 根据角色id返回对应的用户列表
     *
     * @param roleId 角色id
     * @return 用户列表
     */
    List<SysUser> getUserListByRoleId(Long roleId);

    /**
     * 根据菜单id返回对应的用户列表
     *
     * @param menuId 菜单id
     * @return 用户列表
     */
    List<SysUser> getUserListByMenuId(Long menuId);


}
