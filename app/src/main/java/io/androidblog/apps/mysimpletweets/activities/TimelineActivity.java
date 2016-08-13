package io.androidblog.apps.mysimpletweets.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import io.androidblog.apps.mysimpletweets.R;
import io.androidblog.apps.mysimpletweets.adapters.CustomRecyclerViewAdapter;
import io.androidblog.apps.mysimpletweets.fragments.ComposeTweetDialogFragment;
import io.androidblog.apps.mysimpletweets.fragments.TweetsListFragment;
import io.androidblog.apps.mysimpletweets.models.Tweet;
import io.androidblog.apps.mysimpletweets.models.User;
import io.androidblog.apps.mysimpletweets.TwitterApplication;
import io.androidblog.apps.mysimpletweets.network.TwitterClient;
import io.androidblog.apps.mysimpletweets.utils.EndlessRecyclerViewScrollListener;

public class TimelineActivity extends AppCompatActivity implements ComposeTweetDialogFragment.ComposeTweetDialogFragmentListener {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.srlTweets)
    SwipeRefreshLayout srlTweets;
    TweetsListFragment tweetsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }


    @OnClick(R.id.fab)
    public void createTweet() {
        FragmentManager fm = getSupportFragmentManager();
        ComposeTweetDialogFragment editNameDialogFragment = ComposeTweetDialogFragment.newInstance("");
        editNameDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        editNameDialogFragment.show(fm, "");
    }

    @Override
    public void onFinishComposeTweetDialogFragment(final Tweet tweet) {

        /*client.statusesUpdate(new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {

                User user = TwitterApplication.getUser();
                tweet.setUser(user);
                tweetsListFragment.addTweet(tweet);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(TimelineActivity.this, "Error" + responseString.toString(), Toast.LENGTH_SHORT).show();
            }

        }, tweet);*/

    }
}
