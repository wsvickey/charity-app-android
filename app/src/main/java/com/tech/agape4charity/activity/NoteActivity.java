package com.tech.agape4charity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tech.agape4charity.BaseActivity;
import com.tech.agape4charity.R;
import com.tech.agape4charity.object.AppResponse;
import com.tech.agape4charity.object.Organization;
import com.tech.agape4charity.service.RequestEvent;
import com.tech.agape4charity.widget.LinedEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteActivity extends BaseActivity {

    private static final String TAG = "NoteActivity";
    private static final String KEY_ORG = "key.organization";
    private Organization organization;
    @BindView(R.id.edit_story)LinedEditText linedEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        organization = (Organization) getIntent().getParcelableExtra(KEY_ORG);
        Log.d(TAG, "onCreate: Organization : " + organization.toString());
        if(organization.getNote()!=null) {
            linedEditText.setText(organization.getNote().toString());
        }

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "EVENT ID: " + organization.getEventId());
                Log.d(TAG, "Note " + linedEditText.getText().toString());
                RequestEvent requestEvent = new RequestEvent(organization.getEventId(), linedEditText.getText().toString(), RequestEvent.RequestType.ADD_NOTE);
                EventBus.getDefault().post(requestEvent);
                showWaiting(NoteActivity.this);
            }
        });
    }



    public static void startActivity(Context context, Organization organization) {
        Log.i(TAG, "category: " + organization.toString());
        context.startActivity(
                new Intent(context, NoteActivity.class)
                        .putExtra(KEY_ORG, organization)
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(NoteActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(NoteActivity.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AppResponse event){
        dismissWaiting();
        if (event.isSuccess()){
            Toast.makeText(this,event.getMessage(),Toast.LENGTH_SHORT).show();
            organization.setNote(linedEditText.getText().toString());
            finish();
        }
        else {
            Toast.makeText(this,event.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
