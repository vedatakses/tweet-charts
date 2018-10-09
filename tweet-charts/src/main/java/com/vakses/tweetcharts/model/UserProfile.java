package com.vakses.tweetcharts.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by veraxmedax on 07/10/2018.
 */
@Getter
@Setter
public class UserProfile {
    private int tweetCount;
    private int followerCount;
    private long timestamp;

    public UserProfile(int tweetCount, int followerCount, long timestamp) {
        this.tweetCount = tweetCount;
        this.followerCount = followerCount;
        this.timestamp = timestamp;
    }
}
