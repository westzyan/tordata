package com.zyan.tordata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
//@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@SpringBootApplication()
public class TordataApplication {

    public static void main(String[] args) {
        SpringApplication.run(TordataApplication.class, args);
    }

}
