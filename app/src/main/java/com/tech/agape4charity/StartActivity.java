package com.tech.agape4charity;

import android.content.Context;
import android.content.Intent;

import com.tech.agape4charity.activity.ChangePasswordActivity;
import com.tech.agape4charity.activity.SearchActivity;

/**
 * Created by SmasH on 6/13/2018.
 */

public class StartActivity {
    public static void startSearchActivity(Context context) {
        Intent searchIntent = new Intent(context, SearchActivity.class);
        context.startActivity(searchIntent);
        }

    public static void startChangePasswordActivity(Context context) {
        Intent changePwIntent = new Intent(context, ChangePasswordActivity.class);
        context.startActivity(changePwIntent);
        }
}
