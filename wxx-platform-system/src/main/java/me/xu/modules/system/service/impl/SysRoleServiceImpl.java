package me.xu.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.xu.modules.system.pojo.SysRole;
import me.xu.modules.system.mapper.SysRoleMapper;
import me.xu.modules.system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 旭日
 * @since 2021-11-08
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<SysRole> getSysRoleListByUserId(Long userId) {
        List<SysRole> sysRoleList = list(new QueryWrapper<SysRole>().inSql("role_id", "SELECT role_id from sys_user_role where user_id = " + userId));
        if (sysRoleList.size() > 0) {
            return sysRoleList;
        } else {
            return new ArrayList<>();
        }
    }
}
