package com.vakses.tweetcharts.service;

import com.vakses.tweetcharts.model.SentimentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by veraxmedax on 10/10/2018.
 * <p>
 * It gives 1000 credits / month free
 */
@Slf4j
@Service
public class DeepAIClient {

    private static final String SENTIMENT_ANALYSIS_URL = "https://api.deepai.org/api/sentiment-analysis";

    private final String API_KEY;
    private TwitterClient twitterClient;

    @Autowired
    public DeepAIClient(Environment environment, TwitterClient twitterClient) {
        API_KEY = environment.getProperty("deep-ai.apiKey");
        this.twitterClient = twitterClient;
    }

    public SentimentResponse getSentiments(final String user) {
        List<String> tweets = findLastTweets(user);
        tweets.forEach(tweet -> log.info("tweet: {}", tweet));
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> request = preparePostRequest(tweets);
        log.info("requesting sentiments for {} tweets", tweets.size());
        ResponseEntity<SentimentResponse> responseEntity =
                restTemplate.postForEntity(SENTIMENT_ANALYSIS_URL, request, SentimentResponse.class);
        log.info("sentiment response status: {} and contains {} sentiments", responseEntity.getStatusCodeValue(),
                responseEntity.getBody().getOutput().size());
        return responseEntity.getBody();
    }

    private List<String> findLastTweets(final String user) {
        List<String> tweets = new ArrayList<>();
        List<Tweet> mentionedTweets = twitterClient.getLastMentionedTweets(user);

        mentionedTweets.stream().filter(tweet -> !tweet.isRetweet()
                && !tweet.getText().contains("RT") && tweets.size() < 10)
                .forEach(tweet -> tweets.add(tweet.getText()));
        return tweets;
    }

    private HttpEntity<MultiValueMap<String, String>> preparePostRequest(final List<String> tweets) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Api-Key", API_KEY);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("text", createRequestTextFromTweets(tweets));
        return new HttpEntity<>(map, headers);
    }

    private String createRequestTextFromTweets(final List<String> tweets) {
        String text = "";
        for (String tweet : tweets) {
            String clearTweet = tweet.trim();
            final String lastCharacterAsString = String.valueOf(clearTweet.charAt(clearTweet.length() - 1));
            final boolean isEndingWithPunctuation = lastCharacterAsString.matches(".*\\p{Punct}");
            if (!isEndingWithPunctuation) {
                clearTweet += ".";
            }
            text += clearTweet + " ";
        }
        return text.trim();
    }
}
