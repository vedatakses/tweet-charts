package com.vakses.tweetcharts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by veraxmedax on 10/03/2018.
 */
@Component
public class TwitterConfig {

    private final String appId;
    private final String appSecret;
    private final String accessToken;
    private final String accessTokenSecret;

    @Autowired
    public TwitterConfig(final Environment env) {
        this.appId = env.getProperty("spring.social.twitter.app-id");
        this.appSecret = env.getProperty("spring.social.twitter.app-secret");
        this.accessToken = env.getProperty("twitter.oauth.accessToken");
        this.accessTokenSecret = env.getProperty("twitter.oauth.accessToken.secret");
    }

    @Bean
    public TwitterTemplate twitterTemplate() {
        return new TwitterTemplate(appId, appSecret, accessToken, accessTokenSecret);
    }
}