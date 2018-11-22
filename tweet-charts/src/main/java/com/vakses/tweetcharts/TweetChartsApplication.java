package com.vakses.tweetcharts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TweetChartsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TweetChartsApplication.class, args);
    }
}
