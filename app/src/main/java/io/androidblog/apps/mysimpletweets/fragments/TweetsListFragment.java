package io.androidblog.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class TweetsListFragment extends Fragment {

    @BindView(R.id.rvTweets)
    RecyclerView rvTweets;
    ArrayList<Tweet> tweets;
    CustomRecyclerViewAdapter customAdapter;

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
        return v;
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
    }

    public void addTweet(Tweet tweet) {
        customAdapter.insert(0, tweet);
        rvTweets.scrollToPosition(0);
    }
}
