package com.tech.agape4charity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tech.agape4charity.BaseActivity;
import com.tech.agape4charity.R;
import com.tech.agape4charity.SessionManager;
import com.tech.agape4charity.fragment.LoginFragment;
import com.tech.agape4charity.fragment.SignInFragment;
import com.tech.agape4charity.object.LoginResponseEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class InitialActivity extends BaseActivity implements LoginFragment.OnLoginFragmentListener,
        SignInFragment.OnSignInFragmentListener {

    private static final String TAG = "InitialActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        startFragment(R.id.main_fragment, LoginFragment.newInstance(), false);
    }

    @Override
    protected void onStart() {
        super.onStart();

        EventBus.getDefault().register(InitialActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(InitialActivity.this);
    }

    @Override
    public void onSignIn() {
        startFragment(R.id.main_fragment, SignInFragment.newInstance(), true);
    }

    @Override
    public void showLoading() {
        showWaiting(InitialActivity.this);
    }

    @Override
    public void onFgtPw() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginResponseEvent event){
        if (event.isSuccess()){
            dismissWaiting();
            Log.i(TAG, String.valueOf(event.getUser().getUserId()));
            SessionManager.getInstance().setLoginStatus(true);
            SessionManager.getInstance().setUser(event.getUser());

            MainActivity.startActivity(InitialActivity.this);
            InitialActivity.this.finish();
        }else{
            dismissWaiting();
            showToast(InitialActivity.this, event.getMessage());
        }
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, InitialActivity.class));
    }
}
