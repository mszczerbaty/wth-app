package com.example.wth_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WthAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(WthAppApplication.class, args);
    }

}
