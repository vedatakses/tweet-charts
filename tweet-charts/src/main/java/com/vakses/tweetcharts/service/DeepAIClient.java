package com.vakses.tweetcharts.service;

import com.vakses.tweetcharts.model.SentimentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    public DeepAIClient(Environment environment) {
        API_KEY = environment.getProperty("deep-ai.apiKey");
    }

    public SentimentResponse getSentimentsFromTweets(List<String> tweets) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> request = preparePostRequest(tweets);
        ResponseEntity<SentimentResponse> responseEntity = restTemplate.postForEntity(SENTIMENT_ANALYSIS_URL, request, SentimentResponse.class);
        log.info("sentiments response status: {}", responseEntity.getStatusCodeValue());
        return responseEntity.getBody();
    }

    private HttpEntity<MultiValueMap<String, String>> preparePostRequest(List<String> tweets) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Api-Key", API_KEY);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        tweets.forEach(tweet -> {
            // TODO: make sure punctuation mark and one space is added before next tweet
            map.add("text", tweet);
        });
        return new HttpEntity<>(map, headers);
    }
}
