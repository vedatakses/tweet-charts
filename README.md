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

**GET oembed link of last mention:**\
(the html in response should be used to embed the tweet to web site)

```json
{
    "extraData": {},
    "type": "rich",
    "version": "1.0",
    "authorName": "Jassim Al-Kanani",
    "authorUrl": "https://twitter.com/Jassimalkanani",
    "providerName": "Twitter",
    "providerUrl": "https://twitter.com",
    "cacheAge": 3153600000,
    "height": null,
    "width": 400,
    "html": "<blockquote class=\"twitter-tweet\" data-width=\"400\"><p lang=\"en\" dir=\"ltr\">Mmmm designer goods on <a            href=\"https://twitter.com/amazon?ref_src=twsrc%5Etfw\">@amazon</a></p>&mdash; Jassim Al-Kanani (@Jassimalkanani) <a          href=\"https://twitter.com/Jassimalkanani/status/1050783998560088066?ref_src=twsrc%5Etfw\">October 12, 2018</a> 
     </blockquote>\n<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>\n",
    "url": "https://twitter.com/Jassimalkanani/status/1050783998560088066"
}
```
**For more information:**\
https://developer.twitter.com/en/docs/tweets/post-and-engage/api-reference/get-statuses-oembed


## Sentiment Analysis on Tweets

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

**For more information:**\
https://deepai.org/api-docs/#sentiment-analysis
