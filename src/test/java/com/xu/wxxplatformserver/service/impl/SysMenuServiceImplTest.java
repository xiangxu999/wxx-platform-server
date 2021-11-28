package com.xu.wxxplatformserver.service.impl;

import com.xu.wxxplatformserver.pojo.SysMenu;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description SysMenuServiceImplTest
 * Date 2021/11/9 21:27
 * Version 1.0.1
 *
 * @author Wen
 */
@SpringBootTest()
@RunWith(SpringRunner.class)
class SysMenuServiceImplTest {

    @Autowired
    private SysMenuServiceImpl sysMenuService;

    @Test
    void getSysMenuListByUserId() {
        List<SysMenu> sysMenuList = sysMenuService.getSysMenuListByUserId(1L);
        sysMenuList.forEach(System.out::println);
    }
}