package com.hotwave.clubv2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
@MapperScan("com.hotwave.clubv2.mapper")
public class Clubv2Application {
    public static void main(String[] args) {
        SpringApplication.run(Clubv2Application.class, args);
    }

}
