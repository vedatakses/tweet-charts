# tweet-charts
Social Data Analyses with Twitter Data

This project is the backend service of tweet-charts implemented using Spring Boot.

## APIs

Following response generates the follower and tweet counts in dashboard.

**GET user profile by username**
```json
{
    "timestamp": 1539121639,
    "username": "amazon",
    "tweetCount": 28968,
    "followerCount": 2867535
}
```

Following response generates the followers and tweets charts in dashboard.

```json
**GET last 10 (max) user profile by username**
[
    {
        "timestamp": 1539120695,
        "username": "amazon",
        "tweetCount": 28968,
        "followerCount": 2867524
    },
    {
        "timestamp": 1539120570,
        "username": "amazon",
        "tweetCount": 28968,
        "followerCount": 2867522
    },
    {
        "timestamp": 1539120569,
        "username": "amazon",
        "tweetCount": 28968,
        "followerCount": 2867522
    }
]
```
