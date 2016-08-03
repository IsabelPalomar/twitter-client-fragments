package io.androidblog.apps.mysimpletweets.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.activeandroid.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import io.androidblog.apps.mysimpletweets.R;
import io.androidblog.apps.mysimpletweets.TwitterApplication;
import io.androidblog.apps.mysimpletweets.TwitterClient;
import io.androidblog.apps.mysimpletweets.adapters.CustomRecyclerViewAdapter;
import io.androidblog.apps.mysimpletweets.models.Tweet;

public class TimelineActivity extends AppCompatActivity {

    @BindView(R.id.rvTweets)
    RecyclerView rvTweets;
    private TwitterClient client;
    ArrayList<Tweet> tweets;
    CustomRecyclerViewAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        client = TwitterApplication.getRestClient();
        populateTimeline();

        rvTweets.setHasFixedSize(true);
        customAdapter = new CustomRecyclerViewAdapter(this, tweets);
        rvTweets.setAdapter(customAdapter);

    }

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("DEBUG", response.toString());
                tweets.addAll(Tweet.fromJSONArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

}
