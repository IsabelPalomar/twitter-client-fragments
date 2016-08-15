package io.androidblog.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.androidblog.apps.mysimpletweets.R;
import io.androidblog.apps.mysimpletweets.adapters.CustomRecyclerViewAdapter;
import io.androidblog.apps.mysimpletweets.models.Tweet;
import io.androidblog.apps.mysimpletweets.utils.EndlessRecyclerViewScrollListener;

public class TweetsListFragment extends Fragment {

    @BindView(R.id.rvTweets)
    RecyclerView rvTweets;
    ArrayList<Tweet> tweets;
    CustomRecyclerViewAdapter customAdapter;
    @BindView(R.id.srlTweets)
    SwipeRefreshLayout srlTweets;

    //Inflation logic
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        ButterKnife.bind(this, v);

        rvTweets.setAdapter(customAdapter);
        rvTweets.setHasFixedSize(true);

        // Add the scroll listener
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTweets.setLayoutManager(linearLayoutManager);

        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                populateTimeline(25);
            }


        });

        srlTweets.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                populateTimeline(25);
            }
        });

        return v;
    }

    void populateTimeline(int since) {
    }


    //creation lifecycle event
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
        customAdapter = new CustomRecyclerViewAdapter(getActivity(), tweets, getFragmentManager());
    }

    public void addAll(ArrayList<Tweet> responseTweets) {
        tweets.addAll(responseTweets);
        customAdapter.addAll(tweets);
        srlTweets.setRefreshing(false);
    }

    public void addTweet(Tweet tweet) {
        customAdapter.insert(0, tweet);
        rvTweets.scrollToPosition(0);
    }
}
