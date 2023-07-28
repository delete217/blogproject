package com.delete.blogg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.delete.blogg.mapper")
public class BloggApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloggApplication.class, args);
    }

}
