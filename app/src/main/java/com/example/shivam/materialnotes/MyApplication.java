package com.example.shivam.materialnotes;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

/**
 * Created by Shivam on 04/02/15.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "jZ9CoHp9wYeqFoiZbWGqv1xoqkptNcK2Iu6pbAax", "JlsAP9CxeAQ0iO9qd6UOTd2XVfM3tYitndeYuoDg");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    public static void updateInstallation(ParseUser user)
    {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put(Constants.USER_ID, user.getObjectId());
        installation.saveInBackground();
    }

}
