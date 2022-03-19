package me.xu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.xu.pojo.SysRole;
import me.xu.pojo.SysUser;
import me.xu.mapper.SysUserMapper;
import me.xu.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 旭日
 * @since 2021-11-08
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleServiceImpl sysRoleService;


    @Override
    public SysUser getByUserId(Long userId) {
        return getOne(new QueryWrapper<SysUser>().eq("user_id", userId));
    }

    @Override
    public SysUser getByUsername(String username) {
        return getOne(new QueryWrapper<SysUser>().eq("username",username));
    }

    @Override
    public List<Long> getUserRoleList(Long userId) {
        // 得到当前登录用户的角色
        List<SysRole> sysRoleList = sysRoleService.getSysRoleListByUserId(userId);
        if (sysRoleList.size() > 0) {
            return sysRoleList.stream().map(SysRole::getRoleId).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @Override
    public List<SysUser> getUserListByRoleId(Long roleId) {
        return list(new QueryWrapper<SysUser>().inSql("user_id", "SELECT user_id FROM sys_user_role  WHERE role_id = " + roleId));
    }

    @Override
    public List<SysUser> getUserListByMenuId(Long menuId) {
        return sysUserMapper.getSysUserListByMenuId(menuId);
    }

}
