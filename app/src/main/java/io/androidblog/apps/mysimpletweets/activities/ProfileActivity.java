package io.androidblog.apps.mysimpletweets.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import io.androidblog.apps.mysimpletweets.R;
import io.androidblog.apps.mysimpletweets.TwitterApplication;
import io.androidblog.apps.mysimpletweets.fragments.UserTimeLineFragment;
import io.androidblog.apps.mysimpletweets.models.User;
import io.androidblog.apps.mysimpletweets.network.TwitterClient;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    User user;
    @BindView(R.id.ivProfile)
    ImageView ivProfile;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvTagline)
    TextView tvTagline;
    @BindView(R.id.tvFollowing)
    TextView tvFollowing;
    @BindView(R.id.tvFollowers)
    TextView tvFollowers;
    @BindView(R.id.ivBackground)
    ImageView ivBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        String screenName = getIntent().getStringExtra("screen_name");
        boolean getById = getIntent().getBooleanExtra("get_by_id", false);
        long uid = getIntent().getLongExtra("uid", 0);

        client = TwitterApplication.getRestClient();

        if (getById) {
            client.getUserInfoById(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);
                    populateProfileHeader(user);
                }
            }, uid, screenName);
        } else {
            client.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);
                    populateProfileHeader(user);
                }
            });
        }

        if (savedInstanceState == null) {

            UserTimeLineFragment userTimeLineFragment = UserTimeLineFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, userTimeLineFragment);
            ft.commit();
        }

    }

    private void populateProfileHeader(User user) {
        tvUserName.setText(user.getName());
        tvTagline.setText(user.getDescription());
        tvFollowers.setText(user.getFollowers());
        tvFollowing.setText(user.getFollowing());

        Glide.with(this)
                .load(user.getProfileImageUrl())
                .bitmapTransform(new RoundedCornersTransformation(this, 2, 2,
                        RoundedCornersTransformation.CornerType.ALL))
                .into(ivProfile);

        Glide.with(this)
                .load(user.getProfileBannerUrl())
                .into(ivBackground);
    }
}
