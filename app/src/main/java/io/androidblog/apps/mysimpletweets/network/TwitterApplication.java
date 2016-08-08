package io.androidblog.apps.mysimpletweets.network;

import android.content.Context;

import io.androidblog.apps.mysimpletweets.models.User;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     TwitterClient client = TwitterApplication.getRestClient();
 *     // use client to send requests to API
 *
 */
public class TwitterApplication extends com.activeandroid.app.Application {
	private static Context context;
	private static User user;

	@Override
	public void onCreate() {
		super.onCreate();
		TwitterApplication.context = this;

		user = new User();
		user.setUid(1);
		user.setName("Isabel Palomar");
		user.setScreenName("isabelPalomar");
		user.setProfileImageUrl("http://pbs.twimg.com/profile_images/744717614816059392/BbVuLvej_normal.jpg");

	}

	public static TwitterClient getRestClient() {
		return (TwitterClient) TwitterClient.getInstance(TwitterClient.class, TwitterApplication.context);
	}

	public static User getUser(){
		return user;
	}
}