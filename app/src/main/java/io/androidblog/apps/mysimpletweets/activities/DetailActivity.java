package io.androidblog.apps.mysimpletweets.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.androidblog.apps.mysimpletweets.R;
import io.androidblog.apps.mysimpletweets.fragments.ComposeTweetDialogFragment;
import io.androidblog.apps.mysimpletweets.models.Tweet;
import io.androidblog.apps.mysimpletweets.utils.Constants;
import io.androidblog.apps.mysimpletweets.utils.PatternEditableBuilder;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserId)
    TextView tvUserId;
    @BindView(R.id.tvCreatedAt)
    TextView tvCreatedAt;
    @BindView(R.id.tvBody)
    TextView tvBody;
    @BindView(R.id.ivTweetImg)
    ImageView ivTweetImg;
    @BindView(R.id.ivUserImg)
    ImageView ivUserImg;
    @BindView(R.id.btnReply)
    Button btnReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        // Extract book object from intent extras
        Bundle b = getIntent().getExtras();
        final Tweet tweet = b.getParcelable("tweet");

        tvUserName.setText(tweet.getUser().getName());
        tvUserId.setText(Constants.TWEET_PREFIX + tweet.getUser().getScreenName());
        tvCreatedAt.setText(tweet.getCreatedAt());
        tvBody.setText(tweet.getBody());

        Glide.with(this)
                .load(tweet.getUser().getProfileImageUrl())
                .bitmapTransform(new RoundedCornersTransformation(this, 2, 2,
                        RoundedCornersTransformation.CornerType.ALL))
                .into(ivUserImg);

        //Set pattern
        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\@(\\w+)"), ContextCompat.getColor(this, R.color.colorPrimary),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {

                                String url = Constants.TWITTER_BASE_URL + text;

                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);

                            }
                        }).
                addPattern(Pattern.compile("\\#(\\w+)"), ContextCompat.getColor(this, R.color.colorPrimary),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                                text = text.replace("#", "");
                                String url = Constants.TWITTER_HASHTAG_BASE_URL + text;

                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        }).into(tvBody);


        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                ComposeTweetDialogFragment editNameDialogFragment = ComposeTweetDialogFragment.newInstance(Constants.TWEET_PREFIX + tweet.getUser().getScreenName());
                editNameDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                editNameDialogFragment.show(fm, "");
            }
        });

    }
}
