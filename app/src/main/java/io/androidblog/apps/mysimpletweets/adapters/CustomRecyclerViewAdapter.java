package io.androidblog.apps.mysimpletweets.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.androidblog.apps.mysimpletweets.R;
import io.androidblog.apps.mysimpletweets.models.Tweet;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {

    private List<Tweet> tweets;
    private Context context;

    public CustomRecyclerViewAdapter(Context context, ArrayList<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Tweet tweet = tweets.get(position);

        holder.tvBody.setText(tweet.getBody());

    }

    @Override
    public int getItemCount() {
        return tweets.size();
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
