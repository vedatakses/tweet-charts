package com.vakses.tweetcharts.service;

import com.vakses.tweetcharts.model.SentimentResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DeepAIClientTest {

    @Mock
    private TwitterClient twitterClientMock;

    @Mock
    private Environment environmentMock;

    @Mock
    private RestTemplate restTemplateMock;

    @InjectMocks
    private DeepAIClient underTest;

    @Before
    public void setup() {
        initMocks(this);
        when(environmentMock.getProperty("deep-ai.apiKey")).thenReturn("anyKey");
    }

    @Test
    public void shouldGetLastSentiments() {
        // GIVEN
        final String user = "amazon";
        final ResponseEntity<SentimentResponse> responseEntity = new ResponseEntity(createSentimentResponse(), HttpStatus.OK);
        List<Tweet> tweetList = Collections.singletonList(createTweet());
        when(twitterClientMock.getLastMentionedTweets(user)).thenReturn(tweetList);
        when(restTemplateMock.postForEntity(anyString(), any(), eq(SentimentResponse.class))).thenReturn(responseEntity);

        // WHEN
        SentimentResponse sentimentResponse = underTest.getSentiments(user);

        assertNotNull(sentimentResponse);
        assertEquals(1, sentimentResponse.getOutput().size());
        assertEquals("Positive", sentimentResponse.getOutput().get(0));
    }

    private Tweet createTweet() {
        return new Tweet(1L, "Great delivery @amazon!", new Date(1543643040L),
                "anyUser", "anyImageUrl", 2L, 1L, "en", "anySource");
    }

    private SentimentResponse createSentimentResponse() {
        List<String> output = new ArrayList<>();
        output.add("Positive");
        return new SentimentResponse(output);
    }
}
