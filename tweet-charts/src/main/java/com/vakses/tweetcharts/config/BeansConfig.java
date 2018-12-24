package com.vakses.tweetcharts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by veraxmedax on 24/12/2018.
 */
@Configuration
public class BeansConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
