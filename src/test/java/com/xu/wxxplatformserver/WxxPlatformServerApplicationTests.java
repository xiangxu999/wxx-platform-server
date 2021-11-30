package com.xu.wxxplatformserver;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xu.wxxplatformserver.pojo.SysMenu;
import com.xu.wxxplatformserver.pojo.SysRoleMenu;
import com.xu.wxxplatformserver.pojo.SysUser;
import com.xu.wxxplatformserver.service.impl.SysMenuServiceImpl;
import com.xu.wxxplatformserver.service.impl.SysRoleMenuServiceImpl;
import com.xu.wxxplatformserver.service.impl.SysUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
class WxxPlatformServerApplicationTests {
    @Autowired
    private SysRoleMenuServiceImpl sysRoleMenuService;

    @Test
    void contextLoads() {
        List<Long> menuIdList = sysRoleMenuService.list(new QueryWrapper<SysRoleMenu>().eq("menu_id", 6L)).stream().map(SysRoleMenu::getRoleId).collect(Collectors.toList());
        menuIdList.forEach(System.out::println);
    }
}
