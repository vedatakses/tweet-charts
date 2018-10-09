package com.vakses.tweetcharts.controller;

import com.vakses.tweetcharts.model.UserProfile;
import com.vakses.tweetcharts.service.TwitterClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by veraxmedax on 03/10/2018.
 */
@Slf4j
@RestController
@RequestMapping("/api/charts")
public class ChartController {

    private TwitterClient twitterClient;

    @Autowired
    public ChartController(final TwitterClient twitterClient) {
        this.twitterClient = twitterClient;
    }

    @GetMapping(value = "/{user}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable final String user) {
        return ResponseEntity.ok(twitterClient.getUserProfile(user));
    }
}
