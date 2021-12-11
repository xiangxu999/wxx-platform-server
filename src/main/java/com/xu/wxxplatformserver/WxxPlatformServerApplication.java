package com.xu.wxxplatformserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Wen
 */
@SpringBootApplication
@EnableScheduling
public class WxxPlatformServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxxPlatformServerApplication.class, args);
    }

}
