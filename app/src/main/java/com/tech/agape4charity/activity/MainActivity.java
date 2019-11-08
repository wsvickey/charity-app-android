package com.tech.agape4charity.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tech.agape4charity.BaseActivity;
import com.tech.agape4charity.SessionManager;
import com.tech.agape4charity.StartActivity;
import com.tech.agape4charity.fragment.CalendarFragment;
import com.tech.agape4charity.R;
import com.tech.agape4charity.fragment.CategoriesFragment;
import com.tech.agape4charity.fragment.MoreSettingsDialogFragment;
import com.tech.agape4charity.fragment.OrganizationFragment;
import com.tech.agape4charity.fragment.ProfileFragment;
import com.tech.agape4charity.fragment.SearchResultFragment;
import com.tech.agape4charity.object.AddToMyListResponseEvent;
import com.tech.agape4charity.object.AppResponse;
import com.tech.agape4charity.object.Category;
import com.tech.agape4charity.object.CategoryResponseEvent;
import com.tech.agape4charity.object.ItemResponseEvent;
import com.tech.agape4charity.object.Organization;
import com.tech.agape4charity.object.OrganizationResponseEvent;
import com.tech.agape4charity.service.RequestEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements OrganizationFragment.OnMyListFragmentListener, CategoriesFragment.OnCatFragmentListener
        , ProfileFragment.OnProfileFragmentListener,CalendarFragment.OnCalendarFragementListener, SearchResultFragment.OnSearchResultFragmentListener, MoreSettingsDialogFragment.OnMoreSettingsDialogFragmentListener {

    private static final String TAG = "MainActivity";
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private Fragment mFragment;
    private Menu optionMenu;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.navigation_my_list:
                    mFragment = OrganizationFragment.newInstance();
                    startFragment(R.id.main_fragment, mFragment, false);
                    setActionBarTitle(getResources().getString(R.string.title_my_list));
                    optionMenu.findItem(R.id.search).setVisible(false);

                    return true;
                case R.id.navigation_categories:
                    mFragment = CategoriesFragment.newInstance();
                    startFragment(R.id.main_fragment, mFragment, false);
                    setActionBarTitle(getResources().getString(R.string.title_categories));
                    optionMenu.findItem(R.id.search).setVisible(true);
                    return true;
                case R.id.navigation_calendar:
                    mFragment = CalendarFragment.newInstance();
                    startFragment(R.id.main_fragment, mFragment, false);
                    setActionBarTitle(getResources().getString(R.string.title_calendar));
                    optionMenu.findItem(R.id.search).setVisible(false);
                    return true;
                case R.id.navigation_profile:
                    mFragment = ProfileFragment.newInstance();
                    startFragment(R.id.main_fragment, mFragment, false);
                    setActionBarTitle(getResources().getString(R.string.title_profile));
                    optionMenu.findItem(R.id.search).setVisible(false);
                    return true;
            }
            return false;
        }
    };

    private void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(title);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragment = OrganizationFragment.newInstance();
        startFragment(R.id.main_fragment, mFragment, false);
        invalidateOptionsMenu();
        setActionBarTitle(getResources().getString(R.string.title_my_list));

        ButterKnife.bind(this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.search).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search , menu);
        optionMenu = menu;

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);
        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                StartActivity.startSearchActivity(MainActivity.this);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onStart() {
        super.onStart();

        EventBus.getDefault().register(MainActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(MainActivity.this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OrganizationResponseEvent event){
        if (event.isSuccess()){
            if(mFragment instanceof OrganizationFragment){
                ((OrganizationFragment)mFragment).setOrganizations(event.getOrganizations());
            }
            else if(mFragment instanceof ProfileFragment){
                ((ProfileFragment)mFragment).setOrganizations(event.getOrganizations());
            }
            else if (mFragment instanceof CalendarFragment){
                ((CalendarFragment)mFragment).setOrganizations(event.getOrganizations());
            }
        }else{
            dismissWaiting();
            Log.i(TAG,event.getMessage());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ItemResponseEvent event){
        if (event.isSuccess()){
            if(mFragment instanceof CalendarFragment){
                ((CalendarFragment)mFragment).setItem(event.getOrganizations());
            }
        }else{
            dismissWaiting();
            showToast(MainActivity.this, event.getMessage());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CategoryResponseEvent event){
        if (event.isSuccess()){
            if(mFragment instanceof CategoriesFragment){
                ((CategoriesFragment)mFragment).setCategories(event.getCategories());
            }
        }else{
            dismissWaiting();
            showToast(MainActivity.this, event.getMessage());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddToMyListResponseEvent event){
        if (event.isSuccess()){
            showToast(MainActivity.this, event.getMessage());
            dismissWaiting();
        }else{
            showToast(MainActivity.this, event.getMessage());
            dismissWaiting();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AppResponse event){
        dismissWaiting();
        if (event.isSuccess()){
//            Toast.makeText(this,event.getMessage(),Toast.LENGTH_SHORT).show();
        }
        else {
//            Toast.makeText(this,event.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public void onItemClick(Category category) {
        OrganizationActivity.startActivity(MainActivity.this, category);
    }

    @Override
    public void updateDate(long date, Organization organization) {
        showWaiting(this);
        RequestEvent requestEvent = new RequestEvent(organization.getEventId(), date/1000, RequestEvent.RequestType.UPDATE_DATE);
        EventBus.getDefault().post(requestEvent);
//        mFragment = OrganizationFragment.newInstance();
//        startFragment(R.id.main_fragment, mFragment, false);
//        invalidateOptionsMenu();
//        setActionBarTitle("MY LIST");
    }

    @Override
    public void showLoading() {
        showWaiting(this);
        Log.i(TAG, "loading");
    }

    @Override
    public void dismissLoading() {
        dismissWaiting();
        Log.i(TAG, "dismiss");
    }

    @Override
    public void onMoreClick() {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        MoreSettingsDialogFragment newFragment = MoreSettingsDialogFragment.newInstance();
        newFragment.show(ft, "dialog");
    }

    @Override
    public void onChangePassword() {
        StartActivity.startChangePasswordActivity(this);
    }

    @Override
    public void onLogout() {
        SessionManager.getInstance().logout();
        InitialActivity.startActivity(this);
        this.finish();
    }
}
