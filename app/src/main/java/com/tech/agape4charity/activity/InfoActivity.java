package com.tech.agape4charity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tech.agape4charity.BaseActivity;
import com.tech.agape4charity.R;
import com.tech.agape4charity.SessionManager;
import com.tech.agape4charity.object.AddToMyListResponseEvent;
import com.tech.agape4charity.object.Organization;
import com.tech.agape4charity.service.RequestEvent;
import com.tech.agape4charity.utility.Helper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoActivity extends BaseActivity {

    private static final String TAG = "InfoActivity";
    private static final String KEY_ORG = "key.organization";
    private static final String KEY_SCREEN = "key.screen";
    private Organization organization;
    private String userEmail;
    private String organitzationEmail;
    private String subject;
    private String screen;
    @BindView(R.id.btnStatus)Button btnStatus;
    @BindView(R.id.btnAddToMyList)Button btnMyList;
    @BindView(R.id.tvEventText)TextView tvEventDescription;
    @BindView(R.id.btnContact)ImageButton btnContact;
    @BindView(R.id.btnWeb)ImageButton btnWeb;
    @BindView(R.id.btnRemove)Button btnRemove;
    @BindView(R.id.tvCustomText) TextView tvCustomText;
    @BindView(R.id.tvTitle) TextView tvTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor);
        ButterKnife.bind(this);
        onNewIntent(getIntent());
        organization = (Organization) getIntent().getParcelableExtra(KEY_ORG);
        screen = getIntent().getStringExtra(KEY_SCREEN);
        Log.d(TAG, "onCreate: Organization : " + organization.toString());

        initialize();

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(InfoActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(InfoActivity.this);
    }

    private void initialize() {
        if (organization!=null) {
            String custom = organization.getOrgContactNo() + "\n" + organization.getOrgAddress() + "\n" + organization.getOrgEmail();
            tvCustomText.setText(custom);
            tvEventDescription.setText(organization.getOrgDescription());
            tvTitle.setText(organization.getOrgName());
            if (screen.equals("myList")) {
                if (organization.getEventStatus() != null && organization.getEventStatus().equals("PENDING")) {
                    btnStatus.setEnabled(false);
                }
                btnStatus.setText(organization.getEventStatus());
                btnMyList.setVisibility(View.GONE);
                btnRemove.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    public static void startActivity(Context context, Organization organization, String screen) {
        Log.i(TAG, "category: " + organization.toString());
        context.startActivity(
                new Intent(context, InfoActivity.class)
                        .putExtra(KEY_ORG, organization)
                        .putExtra(KEY_SCREEN,screen)

        );
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddToMyListResponseEvent event){
        if (event.isSuccess()){
            showToast(InfoActivity.this, event.getMessage());
            dismissWaiting();
            finish();

        }else{
            showToast(InfoActivity.this, event.getMessage());
            dismissWaiting();
        }
    }

    @OnClick(R.id.btnAddToMyList)
    public void addToMyCharity(View view) {
        showWaiting(this);
        RequestEvent event = new RequestEvent(
                SessionManager.getInstance().getUser().getUserId(), organization.getOrgId(),
                0, "Note", "SIGN UP","location",0
                , RequestEvent.RequestType.ADD_TO_MY_LIST);

        EventBus.getDefault().post(event);
    }

    @OnClick(R.id.btnWeb)
    public void web(View view){
        if(!organization.getOrgWeb().equals("")) {
            Helper.navigateWeb(InfoActivity.this, organization.getOrgWeb());
        }
        else{
            showToast(this,"Website not available");
        }
    }

    @OnClick(R.id.btnContact)
    public void contact(View view){
        if(!organization.getOrgContactNo().equals("")) {
            Helper.openDialer(InfoActivity.this, organization.getOrgContactNo());
        }
        else {
            showToast(this, "Contact number not available");
        }
    }

    @OnClick(R.id.btnRemove)
    public void remove(View view){
        showWaiting(this);
        RequestEvent event = new RequestEvent(organization.getEventId(), RequestEvent.RequestType.ADD_TO_MY_LIST);
        EventBus.getDefault().post(event);
    }

    @OnClick(R.id.btnStatus)
    public void donate(View view) {
        userEmail = SessionManager.getInstance().getUser().getEmail();
        organitzationEmail = organization.getOrgEmail();
        subject = organization.getOrgName();
        Helper.sendEmail(InfoActivity.this,userEmail,organitzationEmail,subject);
    }
}
