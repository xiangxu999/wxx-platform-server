package me.xu.modules.system.service;

import me.xu.modules.system.pojo.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 旭日
 * @since 2021-11-08
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据用户的id得到用户所拥有的角色列表
     *
     * @param userId 用户id
     * @return 角色列表
     */
    List<SysRole> getSysRoleListByUserId(Long userId);
}
