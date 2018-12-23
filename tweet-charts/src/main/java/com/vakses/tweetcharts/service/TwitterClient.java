package com.vakses.tweetcharts.service;

import com.vakses.tweetcharts.model.UserProfile;
import com.vakses.tweetcharts.repository.UserProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.OEmbedOptions;
import org.springframework.social.twitter.api.OEmbedTweet;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by veraxmedax on 07/10/2018.
 */
@Slf4j
@Service
public class TwitterClient {

    private static final String POPULAR_ACCOUNTS_FILE = "popular-accounts.txt";
    private static final String LAST_SEARCH_DATE_FILE = "last_search_date.txt";

    private Twitter twitter;
    private UserProfileRepository userProfileRepository;

    @Autowired
    public TwitterClient(final Twitter twitter, final UserProfileRepository userProfileRepository) {
        this.twitter = twitter;
        this.userProfileRepository = userProfileRepository;
    }

    //@Scheduled(fixedDelayString = "86400000")
    public void searchPopularUserProfiles() {
        if (isDailyJobAlreadyProcessed()) {
            return;
        }
        log.info("Retrieving popular user profiles");
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(classLoader.getResource(POPULAR_ACCOUNTS_FILE).getFile());
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String account = scanner.nextLine();
                getUserProfile(account);
                TimeUnit.MILLISECONDS.sleep(100L);
            }
        } catch (Exception e) {
            log.warn("Error when reading file: {}", POPULAR_ACCOUNTS_FILE);
        }
    }

    private boolean isDailyJobAlreadyProcessed() {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(classLoader.getResource(LAST_SEARCH_DATE_FILE).getFile());
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                final String lastDailyJobDate = scanner.nextLine();
                final String dateToday = todayDateFormat();
                if (lastDailyJobDate.equals(dateToday)) {
                    log.info("Today's job has already processed, skipping processing again..");
                    return true;
                }
                updateLastSearchDate(file, dateToday);
            }
        } catch (Exception e) {
            log.warn("Error when reading file: {}", POPULAR_ACCOUNTS_FILE);
        }
        return false;
    }

    private void updateLastSearchDate(final File file, final String updatingDate) throws IOException {
        final FileWriter fileWriter = new FileWriter(file, false);
        fileWriter.write(updatingDate);
        fileWriter.close();
    }

    private String todayDateFormat() {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        final Date date = new Date();
        return dateFormat.format(date);
    }

    // TODO : write stored profiles to the file (user_profile.csv)
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

    public List<Tweet> getLastMentionedTweets(final String mention) {
        log.info("Retrieving last tweets that @{} mentioned", mention);
        final SearchParameters searchParameters = new SearchParameters("@" + mention).locale("en").lang("en");
        return twitter.searchOperations().search(searchParameters).getTweets();
    }

    public Map<String, Long> getLastLocationsOfMentions(final String user) {
        log.info("Retrieving locations of {} mentions", user);
        final List<String> locations = new ArrayList<>();
        final List<Tweet> tweets = getLastMentionedTweets(user);
        for (Tweet tweet : tweets) {
            if (!tweet.isRetweet() && !tweet.getText().contains("RT")) {
                String location = tweet.getUser().getLocation();
                if (location != null && !location.isEmpty()) {
                    final String filteredLocation = getFilteredLocation(location);
                    if (!filteredLocation.isEmpty()) {
                        locations.add(filteredLocation);
                    }
                }
            }
        }
        log.info("Total of {} locations detected", locations.size());
        return locations.stream().collect(Collectors.groupingBy(e -> e.toString(), Collectors.counting()));
    }

    public OEmbedTweet getLastMentionAsEmbeddedTweet(final String user) {
        log.info("Retrieving last '@{}' mention as embedded tweet", user);
        long tweetId = 0L;
        final List<Tweet> tweets = getLastMentionedTweets(user);
        for (Tweet tweet : tweets) {
            if (!tweet.isRetweet() && !tweet.getText().contains("RT")) {
                tweetId = tweet.getId();
                break;
            }
        }
        if (tweetId > 0) {
            final OEmbedOptions options = new OEmbedOptions();
            options.maxWidth(400);
            options.omitScript();
            return twitter.timelineOperations().getStatusOEmbed(String.valueOf(tweetId), options);
        }
        return null;
    }

    private String getFilteredLocation(String location) {
        final String clearLocation = location.substring(0, 1).toUpperCase() + location.substring(1);
        clearLocation.replaceAll("[^\\p{Alpha}]", "");
        int indexOfComma = clearLocation.indexOf(",");
        if (clearLocation.length() < 2) {
            if (indexOfComma != -1) {
                location = clearLocation.substring(0, indexOfComma);
            } else {
                location = clearLocation;
            }
        }
        return location;
    }
}
