package com.tech.agape4charity.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.tech.agape4charity.BaseActivity;
import com.tech.agape4charity.R;
import com.tech.agape4charity.SessionManager;
import com.tech.agape4charity.adapter.OrgRecyclerViewAdapter;
import com.tech.agape4charity.object.AddToMyListResponseEvent;
import com.tech.agape4charity.object.Category;
import com.tech.agape4charity.object.Organization;
import com.tech.agape4charity.object.OrganizationResponseEvent;
import com.tech.agape4charity.service.RequestEvent;
import com.tech.agape4charity.utility.Helper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrganizationActivity extends BaseActivity implements OrgRecyclerViewAdapter.OrgAdapterCallback{

    private static final String KEY_CATEGORY = "key.category";
    private static final String KEY_CATEGORY_ID = "key.category.id";
    private static final String TAG = "OrgActivity";
    private String userEmail;
    private String organitzationEmail;
    private String subject;
    private long catId;

    @BindView(R.id.recyclerView) RecyclerView view;
    private int mColumnCount = 1;
    SearchView searchView = null;
    private ArrayList<Organization> organizations = new ArrayList<>();
    private OrgRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_organization);

        ButterKnife.bind(this);

        onNewIntent(getIntent());


        if(view instanceof RecyclerView){
            RecyclerView recyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(this, mColumnCount));
            }

            adapter = new OrgRecyclerViewAdapter(organizations, this);
            adapter.setMyCharity(false);
            adapter.setCalendar(false);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.searchaction, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) OrganizationActivity.this.getSystemService(Context.SEARCH_SERVICE);

        searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(OrganizationActivity.this.getComponentName()));
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                RequestEvent requestEvent = new RequestEvent(catId,text, RequestEvent.RequestType.SEARCH_ORG);
                EventBus.getDefault().post(requestEvent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(intent.getStringExtra(KEY_CATEGORY));

        catId = intent.getLongExtra(KEY_CATEGORY_ID,0);
        RequestEvent event = new RequestEvent(intent.getLongExtra(KEY_CATEGORY_ID, 0)
                , RequestEvent.RequestType.GET_ORGANIZATION_FROM_CAT);

        showWaiting(this);
        EventBus.getDefault().post(event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(OrganizationActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(OrganizationActivity.this);
    }

    public static void startActivity(Context context, Category category) {
        Log.i(TAG, "category: " + category.toString());
        context.startActivity(
                new Intent(context, OrganizationActivity.class)
                .putExtra(KEY_CATEGORY, category.getName())
                .putExtra(KEY_CATEGORY_ID, category.getId())
        );
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OrganizationResponseEvent event){
        if (event.isSuccess()){
            adapter.add(event.getOrganizations());
            dismissWaiting();
        }else{
            adapter.clearAll();
             dismissWaiting();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddToMyListResponseEvent event){
        dismissWaiting();
        if (event.isSuccess()){
            showToast(OrganizationActivity.this, event.getMessage());
        }else{
            showToast(OrganizationActivity.this, event.getMessage());
        }
    }

    @Override
    public void onRemove(int position, Organization organization) {

    }

    @Override
    public void addNote(int position, Organization organization) {

    }

    @Override
    public void moreInfo(int position, Organization organization) {
        organization.setScreen("orgScreen");
        InfoActivity.startActivity(OrganizationActivity.this, organization,"orgScreen");
    }
//    public RequestEvent(long userId, long orgId, boolean isActive, long dateTime, String note, String status, String location, RequestType type) {

    @Override
    public void addCharity(int position, Organization organization) {
        showWaiting(this);
        RequestEvent event = new RequestEvent(
                SessionManager.getInstance().getUser().getUserId(), organization.getOrgId(),
                0, "Note", "SIGN UP","location",0
                , RequestEvent.RequestType.ADD_TO_MY_LIST);

        EventBus.getDefault().post(event);
    }

    @Override
    public void signUp(int position, Organization organization) {
        userEmail = SessionManager.getInstance().getUser().getEmail();
        organitzationEmail = organization.getOrgEmail();
        subject = organization.getOrgName();
        Helper.sendEmail(OrganizationActivity.this,userEmail,organitzationEmail,subject);

    }

    @Override
    public void reminder(int position, Organization organization) {

    }
}
