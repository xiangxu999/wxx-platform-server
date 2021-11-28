package com.xu.wxxplatformserver.service.impl;

import com.xu.wxxplatformserver.WxxPlatformServerApplication;
import com.xu.wxxplatformserver.pojo.SysRole;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description SysRoleServiceImplTest
 * Date 2021/11/9 20:52
 * Version 1.0.1
 *
 * @author Wen
 */
@SpringBootTest()
@RunWith(SpringRunner.class)
class SysRoleServiceImplTest {

    @Autowired
    private SysRoleServiceImpl sysRoleService;

    @Test
    void getSysRoleListByUserId() {
        List<SysRole> sysRoleList = sysRoleService.getSysRoleListByUserId(1L);
        sysRoleList.forEach(System.out::println);
    }
}