package me.xu.modules.security.auth;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import me.xu.modules.system.pojo.SysMenu;
import me.xu.modules.system.service.SysMenuService;
import me.xu.modules.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description 自定义权限验证接口扩展
 * Date 2021/11/28 21:01
 * Version 1.0.1
 *
 * @author Wen
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {

        // 1. 声明权限码集合
        List<String> permissionList = new ArrayList<>();

        // 2. 遍历角色列表，查询拥有的权限码
        for (String roleId : getRoleList(loginId, loginType)) {
            SaSession roleSession = SaSessionCustomUtil.getSessionById("role-" + roleId);
            List<String> list = roleSession.get("Permission_List", () -> {
                // 从数据库查询这个角色所拥有的权限列表
                return sysMenuService.getSysMenuListByRoleId(Long.valueOf(roleId)).stream().map(SysMenu::getPerms).collect(Collectors.toList());
            });
            permissionList.addAll(list);
        }

        // 3. 返回权限码集合
        return permissionList;

    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        SaSession session = StpUtil.getSessionByLoginId(loginId);
        return session.get("Role_List", () -> sysUserService.getUserRoleList(StpUtil.getLoginIdAsLong()).stream().map(item -> item + "").collect(Collectors.toList()));
    }

}