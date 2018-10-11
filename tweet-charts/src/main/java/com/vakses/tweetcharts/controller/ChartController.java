package com.vakses.tweetcharts.controller;

import com.vakses.tweetcharts.model.SentimentResponse;
import com.vakses.tweetcharts.model.UserProfile;
import com.vakses.tweetcharts.service.DeepAIClient;
import com.vakses.tweetcharts.service.TwitterClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by veraxmedax on 03/10/2018.
 */
@Slf4j
@RestController
@RequestMapping("/charts")
public class ChartController {

    private TwitterClient twitterClient;
    private DeepAIClient deepAIClient;

    @Autowired
    public ChartController(final TwitterClient twitterClient, final DeepAIClient deepAIClient) {
        this.twitterClient = twitterClient;
        this.deepAIClient = deepAIClient;
    }

    @GetMapping(value = "/profile/{user}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable final String user) {
        return ResponseEntity.ok(twitterClient.getUserProfile(user));
    }

    @GetMapping(value = "/counts/{user}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getLast10UserProfile(@PathVariable final String user) {
        return ResponseEntity.ok(twitterClient.getUserProfilesByTime(user));
    }

    @GetMapping(value = "/sentiments/{user}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SentimentResponse> getSentiments(@PathVariable final String user) {
        return ResponseEntity.ok(deepAIClient.getSentiments(user));
    }

    @GetMapping(value = "/locations/{user}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<String>> getMentionsLocations(@PathVariable final String user) {
        return ResponseEntity.ok(twitterClient.getLastLocationsOfMentions(user));
    }
}
