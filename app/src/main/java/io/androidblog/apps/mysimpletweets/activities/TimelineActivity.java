package io.androidblog.apps.mysimpletweets.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.androidblog.apps.mysimpletweets.R;
import io.androidblog.apps.mysimpletweets.fragments.ComposeTweetDialogFragment;
import io.androidblog.apps.mysimpletweets.fragments.HomeTimelineFragment;
import io.androidblog.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import io.androidblog.apps.mysimpletweets.models.Tweet;

public class TimelineActivity extends AppCompatActivity implements ComposeTweetDialogFragment.ComposeTweetDialogFragmentListener {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tbTwitter)
    Toolbar tbTwitter;
    @BindView(R.id.tlTwitter)
    TabLayout tlTwitter;
    @BindView(R.id.vpTwitter)
    ViewPager vpTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);

        setSupportActionBar(tbTwitter);
        
        setupViewPager(vpTwitter);
        tlTwitter.setupWithViewPager(vpTwitter);

    }

    private void setupViewPager(ViewPager vpTwitter) {
        CustomFragmentPagerAdapter adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeTimelineFragment(), "Home");
        adapter.addFragment(new MentionsTimelineFragment(), "Mentions");
        vpTwitter.setAdapter(adapter);
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

    private class CustomFragmentPagerAdapter extends FragmentPagerAdapter{

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
