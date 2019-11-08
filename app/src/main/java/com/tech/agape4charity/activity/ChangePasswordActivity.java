package com.tech.agape4charity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tech.agape4charity.BaseActivity;
import com.tech.agape4charity.R;
import com.tech.agape4charity.SessionManager;
import com.tech.agape4charity.object.AppResponse;
import com.tech.agape4charity.service.RequestEvent;
import com.tech.agape4charity.utility.Validator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.etCurrentPassword)
    EditText etCurrentPw;
    @BindView(R.id.etNewPassword)
    EditText etNewPassword;
    @BindView(R.id.etRePassword)
    EditText etRePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(ChangePasswordActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(ChangePasswordActivity.this);
    }

    @OnClick(R.id.btnUpdate)
    public void update(View view){
        if(!Validator.isEmpty(etCurrentPw) && Validator.isValidPassword(etNewPassword, etRePassword)){
            long userID = SessionManager.getInstance().getUser().getUserId();
            String currentPassword = etCurrentPw.getText().toString().trim();
            String newPassword = etNewPassword.getText().toString().trim();

            RequestEvent event = new RequestEvent(userID,newPassword,currentPassword, RequestEvent.RequestType.UPDATE_PASSWORD);
            EventBus.getDefault().post(event);
            showWaiting(ChangePasswordActivity.this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AppResponse event){
        dismissWaiting();
        if (event.isSuccess()){
            Toast.makeText(this,event.getMessage(),Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Toast.makeText(this,event.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
