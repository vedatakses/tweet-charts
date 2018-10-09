package com.vakses.tweetcharts.service;

import com.vakses.tweetcharts.model.UserProfile;
import com.vakses.tweetcharts.repository.UserProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Created by veraxmedax on 07/10/2018.
 */
@Slf4j
@Service
public class TwitterClient {

    private Twitter twitter;
    private UserProfileRepository userProfileRepository;

    @Autowired
    public TwitterClient(final Twitter twitter, final UserProfileRepository userProfileRepository) {
        this.twitter = twitter;
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile getUserProfile(final String username) {
        TwitterProfile twitterProfile = twitter.userOperations().getUserProfile(username);
        int tweetCount = twitterProfile.getStatusesCount();
        int followerCount = twitterProfile.getFollowersCount();
        long now = Instant.now().getEpochSecond();
        log.info("Saving user profile for '@{}' at {} with {} tweets and {} followers", username, now, tweetCount, followerCount);
        UserProfile lastProfile = new UserProfile(now, username, tweetCount, followerCount);
        userProfileRepository.save(lastProfile);
        return lastProfile;
    }

    public List<UserProfile> getUserProfilesByTime(final String username) {
        log.info("Retrieving last 10 followers for '@{}' by time", username);
        Pageable pageable = new PageRequest(0, 10, Sort.Direction.DESC, "id");
        return userProfileRepository.findTop10ByUsername(username, pageable);
    }
}
