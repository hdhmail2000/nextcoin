package com.qkwl.service.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by wangchen on 2017-05-23.
 */

@SpringBootApplication
@EnableTransactionManagement
@ImportResource(locations = "classpath:edas-hsf.xml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
