package com.example.wth_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.wth_app")
public class WthAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(WthAppApplication.class, args);
    }

}
