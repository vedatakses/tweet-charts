# tweet-charts
Social Data Analyses with Twitter Data

>**tweet-charts** is the backend service implemented using Spring Boot.

>**tweet-charts-ui** is the frontend service as the name implies implemented using Angular.

## APIs

Following response generates the follower and tweet counts in dashboard.

**GET user profile by username:**
```json
{
    "timestamp": 1539121639,
    "username": "amazon",
    "tweetCount": 28968,
    "followerCount": 2867535
}
```

Following response generates the followers and tweets charts in dashboard.

**GET last 10 (max) user profile by username by timestamp desc:**
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

**GET last 10 (max) sentiments of user mentions:**
```json
{
    "output": [
        "Positive",
        "Neutral",
        "Negative",
        "Negative",
        "Negative",
        "Neutral",
        "Negative",
        "Neutral",
        "Positive",
        "Negative"
    ]
}
```

**GET last 50 (max) locations of user mentions:**\
(not filtering unreal ones)
```json
[
    "Wisconsin",
    "Rome",
    "Paradise",
    "Wisconsin",
    "United States",
    "San Francisco",
    "Wisconsin",
    "Newcastle upon tyne",
    "San Francisco",
    "Malaga",
    "Slovenia",
    "Los Angeles",
    "Walterboro",
    "Wisconsin",
    "Windy City",
    "Moscow",
    "Leesburg",
    "Baltimore"
]
```

## Sentiment Analysis for Tweets

As a sentiment analyses support - 3rd party provider DeepAI is used.

DeepAI provides 1000 credits (request) per month for free.

**Example Request:**\
curl --request POST \
  --url https://api.deepai.org/api/sentiment-analysis \
  --header 'api-key: xxxx' \
  --header 'content-type: multipart/form-data;
  --form 'text=What a great day!'

**Example Response:**
```json
{
    "output": [
        "Positive"
    ]
}
```

**More informations can be found from the following link:**\
https://deepai.org/api-docs/#sentiment-analysis
