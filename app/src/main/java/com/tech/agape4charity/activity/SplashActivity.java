package com.tech.agape4charity.activity;

import android.os.Bundle;

import com.tech.agape4charity.BaseActivity;
import com.tech.agape4charity.SessionManager;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(SessionManager.getInstance().isLoggedIn()){
            MainActivity.startActivity(SplashActivity.this);
        }else{
           InitialActivity.startActivity(SplashActivity.this);
        }
        this.finish();
    }
}
