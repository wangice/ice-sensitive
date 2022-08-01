package com.ice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ice
 * @Date 2022/8/2 0002 00:00
 */
@SpringBootApplication(scanBasePackages = "com.ice")
public class IceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IceApplication.class);
    }
}
