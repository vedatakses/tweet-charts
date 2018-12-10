package com.vakses.tweetcharts.controller;

import com.vakses.tweetcharts.model.SentimentResponse;
import com.vakses.tweetcharts.model.UserProfile;
import com.vakses.tweetcharts.service.DeepAIClient;
import com.vakses.tweetcharts.service.TwitterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.social.twitter.api.OEmbedTweet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by veraxmedax on 03/10/2018.
 */
@RestController
@RequestMapping(value = "/charts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ChartController {

    private final TwitterClient twitterClient;
    private final DeepAIClient deepAIClient;

    @Autowired
    public ChartController(TwitterClient twitterClient, DeepAIClient deepAIClient) {
        this.twitterClient = twitterClient;
        this.deepAIClient = deepAIClient;
    }

    @GetMapping(value = "/profile/{user}")
    public UserProfile getUserProfile(@PathVariable String user) {
        return twitterClient.getUserProfile(user.toLowerCase());
    }

    @GetMapping(value = "/counts/{user}")
    public List<UserProfile> getLast10UserProfile(@PathVariable String user) {
        return twitterClient.getUserProfilesByTime(user.toLowerCase());
    }

    @GetMapping(value = "/sentiments/{user}")
    public SentimentResponse getSentiments(@PathVariable String user) {
        return deepAIClient.getSentiments(user.toLowerCase());
    }

    @GetMapping(value = "/locations/{user}")
    public Map<String, Long> getMentionsLocations(@PathVariable String user) {
        return twitterClient.getLastLocationsOfMentions(user.toLowerCase());
    }

    @GetMapping(value = "/oembed/{user}")
    public String getLastMentionAsEmbeddedTweet(@PathVariable String user) {
        return twitterClient.getLastMentionTweetId(user.toLowerCase());
    }
}
