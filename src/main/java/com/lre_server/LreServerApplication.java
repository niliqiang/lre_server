package com.lre_server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lre_server.dao")
public class LreServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LreServerApplication.class, args);
    }

}
