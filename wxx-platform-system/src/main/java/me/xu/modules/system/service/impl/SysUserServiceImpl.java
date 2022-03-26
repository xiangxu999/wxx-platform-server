package me.xu.modules.system.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xu.common.ResultCode;
import me.xu.consts.CommonConst;
import me.xu.exception.ResultException;
import me.xu.modules.system.mapper.SysUserMapper;
import me.xu.modules.system.pojo.SysRole;
import me.xu.modules.system.pojo.SysUser;
import me.xu.modules.system.pojo.SysUserRole;
import me.xu.modules.system.service.SysRoleService;
import me.xu.modules.system.service.SysUserRoleService;
import me.xu.modules.system.service.SysUserService;
import me.xu.modules.system.service.dto.UserQueryParam;
import me.xu.utils.QueryHelpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 旭日
 * @since 2021-11-08
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public Page<SysUser> userList(Page<SysUser> page, UserQueryParam queryParam) {
        // 查询出来结果
        Page<SysUser> result = page(page, QueryHelpUtil.getPredicate(queryParam));
        // 对结果每一项进行处理，添加角色和把密码置为空
        result.getRecords().forEach(sysUser -> {
            // 密码置为空
            sysUser.setPassword("");
            // 查询该用户对应的角色
            List<SysUserRole> sysUserRoles = sysUserRoleService.list(new QueryWrapper<SysUserRole>().eq("user_id", sysUser.getUserId()));
            if (sysUserRoles.size() > 0) {
                List<Long> roleIds = sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
                List<SysRole> roles = sysRoleService.listByIds(roleIds);
                sysUser.setRoles(roles);
            }
        });
        return result;
    }

    @Override
    public SysUser userInfo(Long id) {
        SysUser result = getById(id);
        result.setPassword("");
        List<SysRole> sysRoles = sysRoleService.getSysRoleListByUserId(id);
        if (sysRoles.size() > 0) {
            result.setRoles(sysRoles);
        }
        return result;
    }

    @Override
    public Boolean userSave(SysUser sysUser) {
        // 设置默认密码
        sysUser.setPassword(SaSecureUtil.md5(CommonConst.USER_PASSWORD));
        // 设置默认头像
        sysUser.setAvatar("https://cdn.lixingyong.com/2021/01/15/QQ20210115152209.jpg");
        return save(sysUser);
    }

    @Override
    public Boolean userUpdate(SysUser sysUser) {
        return updateById(sysUser);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean userDelete(Long[] ids) {
        List<SysUser> sysUsers = listByIds(Arrays.asList(ids));
        sysUsers.forEach(sysUser -> {
            // 清除缓存
            SaSession session = StpUtil.getSessionByLoginId(sysUser.getUserId(), false);
            if (session != null) {
                session.delete("Role_List");
            }
            // 删除中间表
            boolean result = sysUserRoleService.remove(new QueryWrapper<SysUserRole>().eq("user_id", sysUser.getUserId()));
            if (!result) {
                throw new ResultException(ResultCode.FAILED);
            }
        });
        return removeByIds(Arrays.asList(ids));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean roleChange(Long id, Long[] roleIds) {
        // 分配角色为空
        if (roleIds.length == 0) {
            throw new ResultException(ResultCode.ROLE_EMPTY);
        }
        List<SysUserRole> sysUserRoles = new ArrayList<>();

        // 构造中间表数据
        Arrays.stream(roleIds).forEach(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(id);
            sysUserRole.setRoleId(roleId);
            sysUserRoles.add(sysUserRole);
        });

        // 清除缓存
        SysUser sysUser = getById(id);
        SaSession session = StpUtil.getSessionByLoginId(sysUser.getUserId(), false);
        if (session != null) {
            session.delete("Role_List");
        }

        // 删除之前的记录
        sysUserRoleService.remove(new QueryWrapper<SysUserRole>().eq("user_id", id));
        // 添加记录
        if (sysUserRoleService.saveBatch(sysUserRoles)) {
            return true;
        } else {
            throw new ResultException(ResultCode.FAILED);
        }
    }

    @Override
    public Boolean repass(Long id) {
        SysUser sysUser = getById(id);
        String password = SaSecureUtil.md5(CommonConst.USER_PASSWORD);
        sysUser.setPassword(password);
        // 记录重置密码时间
        sysUser.setPwdResetTime(LocalDateTime.parse(DateUtil.now(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        boolean result = updateById(sysUser);
        if (result) {
            // 重置密码后，直接下线
            StpUtil.logout(id);
            return true;
        }
        return false;
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
