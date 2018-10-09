package com.vakses.tweetcharts.service;

import com.vakses.tweetcharts.model.UserProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Created by veraxmedax on 07/10/2018.
 */
@Slf4j
@Service
public class TwitterClient {

    private Twitter twitter;

    @Autowired
    public TwitterClient(Twitter twitter) {
        this.twitter = twitter;
    }

    public UserProfile getUserProfile(final String user) {
        TwitterProfile twitterProfile = twitter.userOperations().getUserProfile(user);
        int tweetCount = twitterProfile.getStatusesCount();
        int followerCount = twitterProfile.getFollowersCount();
        log.info("User '@{}' has {} tweets and {} followers", user, tweetCount, followerCount);
        return new UserProfile(tweetCount, followerCount, Instant.now().getEpochSecond());
    }

    public void getMentionersList(final String user) {
        final String searchTerm = "@" + user;
        SearchResults res = twitter.searchOperations().search(searchTerm);
        System.out.println();
    }
}
