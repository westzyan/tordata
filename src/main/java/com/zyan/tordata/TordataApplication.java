package com.zyan.tordata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TordataApplication {

    public static void main(String[] args) {
        SpringApplication.run(TordataApplication.class, args);
    }

}
