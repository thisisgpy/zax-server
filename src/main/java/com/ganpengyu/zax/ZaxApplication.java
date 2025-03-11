package com.ganpengyu.zax;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
@MapperScan(basePackages = {"com.ganpengyu.zax.dao"})
public class ZaxApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZaxApplication.class, args);
    }

}
