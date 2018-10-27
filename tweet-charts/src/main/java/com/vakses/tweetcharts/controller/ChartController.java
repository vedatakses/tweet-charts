package com.vakses.tweetcharts.controller;

import com.vakses.tweetcharts.model.SentimentResponse;
import com.vakses.tweetcharts.model.UserProfile;
import com.vakses.tweetcharts.service.DeepAIClient;
import com.vakses.tweetcharts.service.TwitterClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.twitter.api.OEmbedTweet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable @NotNull final String user) {
        return ResponseEntity.ok(twitterClient.getUserProfile(user.toLowerCase()));
    }

    @GetMapping(value = "/counts/{user}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<UserProfile>> getLast10UserProfile(@PathVariable @NotNull final String user) {
        return ResponseEntity.ok(twitterClient.getUserProfilesByTime(user.toLowerCase()));
    }

    @GetMapping(value = "/sentiments/{user}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SentimentResponse> getSentiments(@PathVariable @NotNull final String user) {
        return ResponseEntity.ok(deepAIClient.getSentiments(user.toLowerCase()));
    }

    @GetMapping(value = "/locations/{user}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, Long>> getMentionsLocations(@PathVariable @NotNull final String user) {
        return ResponseEntity.ok(twitterClient.getLastLocationsOfMentions(user.toLowerCase()));
    }

    @GetMapping(value = "/oembed/{user}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<OEmbedTweet> getLastMentionAsEmbeddedTweet(@PathVariable @NotNull final String user) {
        return ResponseEntity.ok(twitterClient.getLastMentionAsEmbeddedTweet(user.toLowerCase()));
    }
}
