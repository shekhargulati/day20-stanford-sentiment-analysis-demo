## Day 20 Demo Application##

To deploy it on OpenShift run the following command.

```
$ rhc create-app sentimentsapp jbosseap --from-code=https://github.com/shekhargulati/day20-stanford-sentiment-analysis-demo.git
```

If you have access to medium gears then you can use following command.

```
$ rhc create-app sentimentsapp jbosseap -g medium --from-code=https://github.com/shekhargulati/day20-stanford-sentiment-analysis-demo.git
```

Then set environment variables. Create new twitter app by going to http://dev.twitter.com/apps/new

```
$ rhc env set TWITTER_OAUTH_ACCESS_TOKEN=<please enter value> -a sentimentsapp

$ rhc env set TWITTER_OAUTH_ACCESS_TOKEN_SECRET=<please enter value> -a sentimentsapp

$rhc env set TWITTER_OAUTH_CONSUMER_KEY=<please enter value> -a sentimentsapp

$rhc env set TWITTER_OAUTH_CONSUMER_SECRET=<please enter value> -a sentimentsapp
```