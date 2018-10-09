# tweet-charts
Social Data Analyses with Twitter Data

>**tweet-charts** is the backend service implemented using Spring Boot.

>**tweet-charts-ui** is the frontend service as the name implies implemented using Angular.

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

**GET last 10 (max) user profile by username and timestamp**
```json
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
