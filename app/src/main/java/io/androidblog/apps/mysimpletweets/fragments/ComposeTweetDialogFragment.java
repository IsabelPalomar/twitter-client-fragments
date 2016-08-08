package io.androidblog.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.androidblog.apps.mysimpletweets.R;
import io.androidblog.apps.mysimpletweets.models.Tweet;
import io.androidblog.apps.mysimpletweets.utils.Constants;

public class ComposeTweetDialogFragment extends DialogFragment {

    @BindView(R.id.etTweet)
    EditText etTweet;
    @BindView(R.id.btnClose)
    Button btnClose;
    @BindView(R.id.tvChars)
    TextView tvChars;
    String replyTo;

    public ComposeTweetDialogFragment() {
    }

    public static ComposeTweetDialogFragment newInstance(String replyTo) {
        ComposeTweetDialogFragment frag = new ComposeTweetDialogFragment();
        Bundle args = new Bundle();
        args.putString("reply", replyTo);
        frag.setArguments(args);
        return frag;
    }


    // 1. Defines the listener interface with a method passing back data result.
    public interface ComposeTweetDialogFragmentListener {
        void onFinishComposeTweetDialogFragment(Tweet tweet);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweet, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        replyTo = getArguments().getString("reply");

        if(!replyTo.equals(Constants.EMPTY_STRING))
            etTweet.setText(replyTo + Constants.EMPTY_STRING);


        final TextWatcher mTextEditorWatcher = new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvChars.setText(String.valueOf(140 - s.length()));
            }

            public void afterTextChanged(Editable s) {
            }
        };

        etTweet.addTextChangedListener(mTextEditorWatcher);

        // Show soft keyboard automatically and request focus to field
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        etTweet.requestFocus();

    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
    }

    @OnClick(R.id.btnClose)
    public void closeDialog() {
        dismiss();
    }

    @OnClick(R.id.btnPostTweet)
    public void postTweet() {

        String text = etTweet.getText().toString();

        if(!text.equals(Constants.EMPTY_STRING)){

            Tweet tweet = new Tweet();
            tweet.setBody(text);

            ComposeTweetDialogFragmentListener listener = (ComposeTweetDialogFragmentListener) getActivity();
            listener.onFinishComposeTweetDialogFragment(tweet);
            dismiss();
        }


    }

}
