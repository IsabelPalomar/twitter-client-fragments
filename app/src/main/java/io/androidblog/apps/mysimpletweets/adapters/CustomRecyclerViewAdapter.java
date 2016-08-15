package io.androidblog.apps.mysimpletweets.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.androidblog.apps.mysimpletweets.R;
import io.androidblog.apps.mysimpletweets.activities.DetailActivity;
import io.androidblog.apps.mysimpletweets.activities.ProfileActivity;
import io.androidblog.apps.mysimpletweets.activities.TimelineActivity;
import io.androidblog.apps.mysimpletweets.fragments.ComposeTweetDialogFragment;
import io.androidblog.apps.mysimpletweets.models.Tweet;
import io.androidblog.apps.mysimpletweets.utils.Constants;
import io.androidblog.apps.mysimpletweets.utils.PatternEditableBuilder;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {

    private List<Tweet> tweets;
    private Context context;
    private FragmentManager supportFragmentManager;

    public CustomRecyclerViewAdapter(Context context, ArrayList<Tweet> tweets, FragmentManager supportFragmentManager) {
        this.context = context;
        this.tweets = tweets;
        this.supportFragmentManager = supportFragmentManager;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View tweetView = inflater.inflate(R.layout.tweet_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Tweet tweet = tweets.get(position);

        holder.tvUserName.setText(tweet.getUser().getName());
        holder.tvUserId.setText(Constants.TWEET_PREFIX + tweet.getUser().getScreenName());
        holder.tvCreatedAt.setText(tweet.getCreatedAt());
        holder.tvBody.setText(tweet.getBody());

        Glide.with(getContext())
                .load(tweet.getUser().getProfileImageUrl())
                .bitmapTransform(new RoundedCornersTransformation(getContext(), 2, 2,
                RoundedCornersTransformation.CornerType.ALL))
                .into(holder.ivUserImg);

        //Set pattern
        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\@(\\w+)"), ContextCompat.getColor(getContext(), R.color.colorPrimary),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {

                                String url = Constants.TWITTER_BASE_URL + text;

                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                getContext().startActivity(i);

                            }
                        }).
                addPattern(Pattern.compile("\\#(\\w+)"), ContextCompat.getColor(getContext(), R.color.colorPrimary),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                                text = text.replace("#", "");
                                String url = Constants.TWITTER_HASHTAG_BASE_URL + text;

                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                getContext().startActivity(i);
                            }
                        }).into(holder.tvBody);


        holder.btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = supportFragmentManager;
                ComposeTweetDialogFragment editNameDialogFragment = ComposeTweetDialogFragment.newInstance(Constants.TWEET_PREFIX + tweet.getUser().getScreenName());
                editNameDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                editNameDialogFragment.show(fm, "");
            }
        });

        holder.tvBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), DetailActivity.class);
                i.putExtra("tweet", tweet);
                getContext().startActivity(i);

            }
        });

        holder.ivUserImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("screen_name", tweet.getUser().getScreenName());
                i.putExtra("get_by_id", true);
                i.putExtra("uid", tweet.getUser().getUid());
                getContext().startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public void addAll(ArrayList<Tweet> tweets) {
        tweets.addAll(tweets);
        notifyDataSetChanged();
    }

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }


    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, Tweet tweet) {
        tweets.add(position, tweet);
        notifyItemInserted(position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivUserImg)
        ImageView ivUserImg;
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
        @BindView(R.id.btnReply)
        Button btnReply;
        @BindView(R.id.btnRetweet)
        Button btnRetweet;
        @BindView(R.id.btnLike)
        Button btnLike;
        @BindView(R.id.btnDM)
        Button btnDM;
        @BindView(R.id.rlTweet)
        RelativeLayout rlTweet;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
