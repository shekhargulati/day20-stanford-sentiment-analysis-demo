package com.sentiments.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import twitter4j.Status;

import com.sentiments.analyzers.SentimentAnalyzer;
import com.sentiments.analyzers.TweetWithSentiment;
import com.sentiments.twitter.TwitterSearch;

@Path("sentiments")
public class SentimentsResource {

    @Inject
    private SentimentAnalyzer sentimentAnalyzer;

    @Inject
    private TwitterSearch twitterSearch;

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public List<Result> sentiments(@QueryParam("searchKeywords") String searchKeywords) {
        List<Result> results = new ArrayList<>();
        if (searchKeywords == null || searchKeywords.length() == 0) {
            return results;
        }

        Set<String> keywords = new HashSet<>();
        for (String keyword : searchKeywords.split(",")) {
            keywords.add(keyword.trim().toLowerCase());
        }
        if (keywords.size() > 3) {
            keywords = new HashSet<>(new ArrayList<>(keywords).subList(0, 3));
        }
        for (String keyword : keywords) {
            List<Status> statuses = twitterSearch.search(keyword);
            System.out.println("Found statuses ... " + statuses.size());
            List<TweetWithSentiment> sentiments = new ArrayList<>();
            for (Status status : statuses) {
                TweetWithSentiment tweetWithSentiment = sentimentAnalyzer.findSentiment(status.getText());
                if (tweetWithSentiment != null) {
                    sentiments.add(tweetWithSentiment);
                }
            }

            Result result = new Result(keyword, sentiments);
            results.add(result);
        }
        return results;
    }

    @GET
    @Path(value = "/text")
    @Produces(value = MediaType.APPLICATION_JSON)
    public TweetWithSentiment findNer(@QueryParam("text") String text) {
        if (text == null || text.length() == 0) {
            return null;
        }
        TweetWithSentiment tweetWithSentiment = sentimentAnalyzer.findSentiment(text);
        return tweetWithSentiment;
    }
}
