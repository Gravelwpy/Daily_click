package com.restful.daily_click;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.restful.daily_click.entity")
public class DailyClickApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyClickApplication.class, args);
    }

}
