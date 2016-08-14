package io.androidblog.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import io.androidblog.apps.mysimpletweets.R;
import io.androidblog.apps.mysimpletweets.TwitterApplication;
import io.androidblog.apps.mysimpletweets.models.Tweet;
import io.androidblog.apps.mysimpletweets.network.TwitterClient;

public class MentionsTimelineFragment extends TweetsListFragment{
    private TwitterClient client;
    int since = 1;
    int count = 25;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient();
        populateTimeline();

                /*rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                since += 25;
                populateTimeline();
            }
        });*/

        /*srlTweets.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                populateTimeline();
            }
        });*/

    }

    private void populateTimeline() {
        client.geMentionsTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //tweets.addAll(Tweet.fromJSONArray(response));
                //customAdapter.addAll(tweets);

                addAll(Tweet.fromJSONArray(response));

                //srlTweets.setRefreshing(false);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
                Toast.makeText(getActivity(), "Error" + errorResponse.toString(), Toast.LENGTH_SHORT).show();
            }
        }, since, count);
    }
}
