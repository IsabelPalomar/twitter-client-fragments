package io.androidblog.apps.mysimpletweets.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import cz.msebera.android.httpclient.Header;

import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.androidblog.apps.mysimpletweets.R;
import io.androidblog.apps.mysimpletweets.TwitterApplication;
import io.androidblog.apps.mysimpletweets.fragments.ComposeTweetDialogFragment;
import io.androidblog.apps.mysimpletweets.fragments.HomeTimelineFragment;
import io.androidblog.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import io.androidblog.apps.mysimpletweets.fragments.TweetsListFragment;
import io.androidblog.apps.mysimpletweets.models.Tweet;
import io.androidblog.apps.mysimpletweets.models.User;
import io.androidblog.apps.mysimpletweets.network.TwitterClient;

public class TimelineActivity extends AppCompatActivity implements ComposeTweetDialogFragment.ComposeTweetDialogFragmentListener {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tbTwitter)
    Toolbar tbTwitter;
    @BindView(R.id.tlTwitter)
    TabLayout tlTwitter;
    @BindView(R.id.vpTwitter)
    ViewPager vpTwitter;
    @BindView(R.id.ndTweets)
    DrawerLayout ndTweets;
    @BindView(R.id.nvViewTweets)
    NavigationView nvViewTweets;

    private ActionBarDrawerToggle drawerToggle;
    TwitterClient client;
    TweetsListFragment tweetsListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);

        setSupportActionBar(tbTwitter);

        setupViewPager(vpTwitter);
        tlTwitter.setupWithViewPager(vpTwitter);

        setupDrawerContent(nvViewTweets);

        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        ndTweets.addDrawerListener(drawerToggle);
        client = TwitterApplication.getRestClient();

        if(savedInstanceState == null){
           tweetsListFragment = (TweetsListFragment) getSupportFragmentManager().findFragmentByTag("TweetsListFragment");
        }

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, ndTweets, tbTwitter, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }

                    
                });

    }

    private void selectDrawerItem(MenuItem menuItem) {

        switch(menuItem.getItemId()) {
            case R.id.navProfile:
                Intent i = new Intent(this, ProfileActivity.class);
                startActivity(i);
                break;
            case R.id.navLogout:
                client.clearAccessToken();
                this.finish();
                break;
        }

        // Close the navigation drawer
        ndTweets.closeDrawers();

    }

    private void setupViewPager(ViewPager vpTwitter) {
        CustomFragmentPagerAdapter adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeTimelineFragment(), "Timeline");
        adapter.addFragment(new MentionsTimelineFragment(), "Mentions");
        vpTwitter.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                ndTweets.openDrawer(GravityCompat.START);
                return true;
        }

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
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

        client.statusesUpdate(new TextHttpResponseHandler() {

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

        }, tweet);

    }

    private class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public CustomFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
