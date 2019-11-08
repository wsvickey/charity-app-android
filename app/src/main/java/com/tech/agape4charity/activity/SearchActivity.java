package com.tech.agape4charity.activity;

import android.app.SearchManager;
import android.content.Context;
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

public class SearchActivity extends BaseActivity implements OrgRecyclerViewAdapter.OrgAdapterCallback {
    public static final String KEY_CAT_ID = "SearchActivity.cat_id";
    private static final String TAG = "SearchActivity";
    private Menu optionMenu;
    SearchView searchView = null;
    @BindView(R.id.recyclerView) RecyclerView view;
    private int mColumnCount = 1;
    private ArrayList<Organization> organizations = new ArrayList<>();
    private OrgRecyclerViewAdapter adapter;
    private String userEmail;
    private String organitzationEmail;
    private String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_organization);
        ButterKnife.bind(this);
        if(view instanceof RecyclerView){
            RecyclerView recyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(this, mColumnCount));
            }

            adapter = new OrgRecyclerViewAdapter(organizations, this);
            adapter.setMyCharity(false);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.searchaction, menu);
        optionMenu = menu;
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) SearchActivity.this.getSystemService(Context.SEARCH_SERVICE);

        searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setIconifiedByDefault(false);
            searchView.requestFocus();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SearchActivity.this.getComponentName()));
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                RequestEvent requestEvent = new RequestEvent(text, RequestEvent.RequestType.SEARCH);
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
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(SearchActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(SearchActivity.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OrganizationResponseEvent event){
        dismissWaiting();
        if (event.isSuccess()){
            adapter.add(event.getOrganizations());
        }
        else {
            adapter.clearAll();
//            Toast.makeText(this,event.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddToMyListResponseEvent event){
        dismissWaiting();
        if (event.isSuccess()){
            showToast(SearchActivity.this, event.getMessage());
        }else{
            showToast(SearchActivity.this, event.getMessage());
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
        Log.i(TAG,"organization - " + organization.getOrgName());
        InfoActivity.startActivity(SearchActivity.this, organization,"orgScreen");
    }

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
        Helper.sendEmail(SearchActivity.this,userEmail,organitzationEmail,subject);
    }

    @Override
    public void reminder(int position, Organization organization) {

    }
}
