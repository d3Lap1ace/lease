package com.spring.lease;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // 原神 启动
public class AdminWebApplication {
    public static void main(String[] args) {

        SpringApplication.run(AdminWebApplication.class,args);
    }
}