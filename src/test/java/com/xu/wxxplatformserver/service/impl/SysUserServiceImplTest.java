package com.xu.wxxplatformserver.service.impl;

import com.xu.wxxplatformserver.pojo.SysUser;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description SysUserServiceImplTest
 * Date 2021/11/9 22:37
 * Version 1.0.1
 *
 * @author Wen
 */
@SpringBootTest()
@RunWith(SpringRunner.class)
class SysUserServiceImplTest {

    @Autowired
    private SysUserServiceImpl sysUserService;

    @Test
    void getByUsername() {
        SysUser admin = sysUserService.getByUsername("admin");
        System.out.println(admin);
    }

    @Test
    void getUserAuthorityInfo() {
        String userAuthorityInfo = sysUserService.getUserAuthorityInfo(2L);
        System.out.println(userAuthorityInfo);
    }

    @Test
    void getUserListByRoleId() {
        List<SysUser> sysUserList = sysUserService.getUserListByRoleId(6L);
        sysUserList.forEach(System.out::println);
    }

    @Test
    void getUserListByMenuId() {
        List<SysUser> sysUserList = sysUserService.getUserListByMenuId(2L);
        sysUserList.forEach(System.out::println);
    }

    @Test
    void clearUserAuthorityInfo() {
    }

    @Test
    void clearUserAuthorityInfoByRoleId() {
    }

    @Test
    void clearUserAuthorityInfoByMenuId() {
    }
}