package com.xu.wxxplatformserver;

import com.xu.wxxplatformserver.properties.CaptchaProperties;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
class WxxPlatformServerApplicationTests {

    @Autowired
    private CaptchaProperties captchaProperties;

    @Test
    void contextLoads() {
    }
}
