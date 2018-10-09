package com.vakses.tweetcharts.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by veraxmedax on 07/10/2018.
 */
@Entity
@Data
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long timestamp;
    private String username;
    private int tweetCount;
    private int followerCount;

    public UserProfile() {

    }

    public UserProfile(long timestamp, String username, int tweetCount, int followerCount) {
        this.timestamp = timestamp;
        this.username = username;
        this.tweetCount = tweetCount;
        this.followerCount = followerCount;
    }

    public long getId() {
        return id;
    }
}
