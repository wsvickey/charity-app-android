package com.tech.agape4charity.service;

import android.content.Context;
import android.util.Log;

import com.tech.agape4charity.AppConstant;
import com.tech.agape4charity.object.AddToMyListResponseEvent;
import com.tech.agape4charity.object.AppResponse;
import com.tech.agape4charity.object.CategoryResponseEvent;
import com.tech.agape4charity.object.ItemResponseEvent;
import com.tech.agape4charity.object.LoginResponseEvent;
import com.tech.agape4charity.object.OrganizationResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Charitha Ratnayake on 2/15/2018.
 */

public class Controller {
    private static final String TAG = "Controller";

    public void setUrl(CharityApi mApi)
    {
        this.BASE_URL = "http://api.agape4charity.com/";
        //192.168.1.50:8069 http://lk.techleadintl.com:52001/api/signin/'
        //http://ec2-54-177-249-125.us-west-1.compute.amazonaws.com
        //http://ec2-54-177-249-125.us-west-1.compute.amazonaws.com
    }
    private String BASE_URL = "http://api.agape4charity.com/";
    private CharityApi mApi;

    public Controller(Context context) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mApi = retrofit.create(CharityApi.class);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(RequestEvent event){
        switch (event.getType()){
            case LOGIN:
                login(event);
                break;
            case REGISTER:
                register(event);
                break;
            case MY_LIST:
                getMyList(event);
                break;
            case GET_CATEGORIES:
                getCategories(event);
                break;
            case GET_ORGANIZATION_FROM_CAT:
                getOrganizations(event);
                break;
            case ADD_TO_MY_LIST:
                addToMyList(event);
                break;
            case SEARCH:
                search(event);
                break;
            case ADD_NOTE:
                addNote(event);
                break;
            case UPDATE_DATE:
                updateDate(event);
                break;
            case GET_REMINDER:
                getReminderItem(event);
                break;
            case SEARCH_ORG:
                searchOrg(event);
                break;
            case UPDATE_PASSWORD:
                updatePassword(event);
                break;
        }
    }

    private void search(RequestEvent event) {
        Log.i(TAG, "search request");
        mApi.search(event).enqueue(new Callback<OrganizationResponseEvent>() {
            @Override
            public void onResponse(Call<OrganizationResponseEvent> call, Response<OrganizationResponseEvent> response) {
                OrganizationResponseEvent responseEvent = response.body();
                if(response.isSuccessful()){
                    Log.i(TAG, "search --> success");
                }
                else {
                    Log.i(TAG, "search --> fail");
                }
                EventBus.getDefault().post(responseEvent);
            }

            @Override
            public void onFailure(Call<OrganizationResponseEvent> call, Throwable t) {
                Log.i(TAG, "search --> onFailure: " + t.getMessage());
                OrganizationResponseEvent responseEvent = new OrganizationResponseEvent();
                responseEvent.setError(AppConstant.FAILURE_RESULT, t);
                EventBus.getDefault().post(responseEvent);
            }
        });
    }

    private void addToMyList(RequestEvent event){
        Log.i(TAG, "addToMyList request");
        mApi.addToMyList(event).enqueue(new Callback<AddToMyListResponseEvent>() {
            @Override
            public void onResponse(Call<AddToMyListResponseEvent> call, Response<AddToMyListResponseEvent> response) {
                AddToMyListResponseEvent responseEvent = response.body();
                if(response.isSuccessful()){
                    Log.i(TAG, "addToMyList --> success");
                    EventBus.getDefault().post(responseEvent);
                }else{
                    Log.i(TAG, "addToMyList --> fail");
                    EventBus.getDefault().post(responseEvent);
                }
            }

            @Override
            public void onFailure(Call<AddToMyListResponseEvent> call, Throwable t) {
                Log.i(TAG, "addToMyList --> onFailure: " + t.getMessage());
                AddToMyListResponseEvent responseEvent = new AddToMyListResponseEvent();
                responseEvent.setError(AppConstant.FAILURE_RESULT, t);
                EventBus.getDefault().post(responseEvent);
            }
        });
    }

    private void getOrganizations(RequestEvent event){
        Log.i(TAG, "getOrganizations request");
        mApi.getOrganizations(event).enqueue(new Callback<OrganizationResponseEvent>() {
            @Override
            public void onResponse(Call<OrganizationResponseEvent> call, Response<OrganizationResponseEvent> response) {
                OrganizationResponseEvent responseEvent = response.body();
                if(response.isSuccessful()){
                    Log.i(TAG, "getOrganizations --> success");
                    EventBus.getDefault().post(responseEvent);
                }else{
                    Log.i(TAG, "getOrganizations --> fail");
                    EventBus.getDefault().post(responseEvent);
                }
            }

            @Override
            public void onFailure(Call<OrganizationResponseEvent> call, Throwable t) {
                Log.i(TAG, "getOrganizations --> onFailure: " + t.getMessage());
                OrganizationResponseEvent responseEvent = new OrganizationResponseEvent();
                responseEvent.setError(AppConstant.FAILURE_RESULT, t);
                EventBus.getDefault().post(responseEvent);
            }
        });
    }

    private void getCategories(RequestEvent event){
        Log.i(TAG, "getMyList request");
        mApi.getCategories(event).enqueue(new Callback<CategoryResponseEvent>() {
            @Override
            public void onResponse(Call<CategoryResponseEvent> call, Response<CategoryResponseEvent> response) {
                CategoryResponseEvent responseEvent = response.body();
                if(response.isSuccessful()){
                    Log.i(TAG, "getMyList --> success");
                    EventBus.getDefault().post(responseEvent);
                }else{
                    Log.i(TAG, "getMyList --> fail");
                    EventBus.getDefault().post(responseEvent);
                }
            }

            @Override
            public void onFailure(Call<CategoryResponseEvent> call, Throwable t) {
                Log.i(TAG, "getMyList --> onFailure: " + t.getMessage());
                CategoryResponseEvent responseEvent = new CategoryResponseEvent();
                responseEvent.setError(AppConstant.FAILURE_RESULT, t);
                EventBus.getDefault().post(responseEvent);
            }
        });
    }

    private void getMyList(RequestEvent event){
        Log.i(TAG, "getMyList request");
        mApi.getMyList(event).enqueue(new Callback<OrganizationResponseEvent>() {
            @Override
            public void onResponse(Call<OrganizationResponseEvent> call, Response<OrganizationResponseEvent> response) {
                OrganizationResponseEvent responseEvent = response.body();
                if(response.isSuccessful()){
                    Log.i(TAG, "getMyList --> success");
                    EventBus.getDefault().post(responseEvent);
                }else{
                    Log.i(TAG, "getMyList --> fail");
                    EventBus.getDefault().post(responseEvent);
                }
            }

            @Override
            public void onFailure(Call<OrganizationResponseEvent> call, Throwable t) {
                Log.i(TAG, "getMyList --> onFailure: " + t.getMessage());
                OrganizationResponseEvent responseEvent = new OrganizationResponseEvent();
                responseEvent.setError(AppConstant.FAILURE_RESULT, t);
                EventBus.getDefault().post(responseEvent);
            }
        });
    }

    private void login(RequestEvent event){
        Log.i(TAG, "login request");
        mApi.signin(event).enqueue(new Callback<LoginResponseEvent>() {
            @Override
            public void onResponse(Call<LoginResponseEvent> call, Response<LoginResponseEvent> response) {
                LoginResponseEvent responseEvent = response.body();
                if(response.isSuccessful()){
                    Log.i(TAG, "login --> success");
                    EventBus.getDefault().post(responseEvent);
                }else{
                    Log.i(TAG, "login --> fail");
                    EventBus.getDefault().post(responseEvent);
                }
            }

            @Override
            public void onFailure(Call<LoginResponseEvent> call, Throwable t) {
                Log.i(TAG, "login --> onFailure: " + t.getMessage());
                LoginResponseEvent responseEvent = new LoginResponseEvent();
                responseEvent.setError(AppConstant.FAILURE_RESULT, t);
                EventBus.getDefault().post(responseEvent);
            }
        });
    }

    private void register(RequestEvent event){
        Log.i(TAG, "register request");
        mApi.signup(event).enqueue(new Callback<LoginResponseEvent>() {
            @Override
            public void onResponse(Call<LoginResponseEvent> call, Response<LoginResponseEvent> response) {
                LoginResponseEvent responseEvent = response.body();
                if(response.isSuccessful()){
                    Log.i(TAG, "register --> success");
                    EventBus.getDefault().post(responseEvent);
                }else{
                    Log.i(TAG, "register --> fail");
                    EventBus.getDefault().post(responseEvent);
                }
            }

            @Override
            public void onFailure(Call<LoginResponseEvent> call, Throwable t) {
                Log.i(TAG, "register --> onFailure: " + t.getMessage());
                LoginResponseEvent responseEvent = new LoginResponseEvent();
                responseEvent.setError(AppConstant.FAILURE_RESULT, t);
                EventBus.getDefault().post(responseEvent);
            }
        });
    }

    private void addNote(RequestEvent event){
        Log.i(TAG, "note request");
        mApi.addNote(event).enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse responseEvent = response.body();
                if(response.isSuccessful()){
                    Log.i(TAG, "add note --> success");
                    EventBus.getDefault().post(responseEvent);
                }else{
                    Log.i(TAG, "add note --> fail");
                    EventBus.getDefault().post(responseEvent);
                }
            }

            @Override
            public void onFailure(Call<AppResponse> call, Throwable t) {
                Log.i(TAG, "add note --> onFailure: " + t.getMessage());
                AppResponse responseEvent = new AppResponse();
                responseEvent.setError(AppConstant.FAILURE_RESULT, t);
                EventBus.getDefault().post(responseEvent);
            }
        });
    }

    private void updateDate(RequestEvent event){
        Log.i(TAG, "updateDate request");
        mApi.updateDate(event).enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse responseEvent = response.body();
                if(response.isSuccessful()){
                    Log.i(TAG, "add updateDate --> success");
                    EventBus.getDefault().post(responseEvent);
                }else{
                    Log.i(TAG, "add updateDate --> fail");
                    EventBus.getDefault().post(responseEvent);
                }
            }

            @Override
            public void onFailure(Call<AppResponse> call, Throwable t) {
                Log.i(TAG, "add note --> updateDate: " + t.getMessage());
                AppResponse responseEvent = new AppResponse();
                responseEvent.setError(AppConstant.FAILURE_RESULT, t);
                EventBus.getDefault().post(responseEvent);
            }
        });
    }

    private void getReminderItem(RequestEvent event){
        Log.i(TAG, "getReminderItem request");
        mApi.getReminder(event).enqueue(new Callback<ItemResponseEvent>() {
            @Override
            public void onResponse(Call<ItemResponseEvent> call, Response<ItemResponseEvent> response) {
                ItemResponseEvent responseEvent = response.body();
                if(response.isSuccessful()){
                    Log.i(TAG, "add getReminderItem --> success");
                    EventBus.getDefault().post(responseEvent);
                }else{
                    Log.i(TAG, "add getReminderItem --> fail");
                    EventBus.getDefault().post(responseEvent);
                }
            }

            @Override
            public void onFailure(Call<ItemResponseEvent> call, Throwable t) {
                Log.i(TAG, "add getReminderItem --> onFailure: " + t.getMessage());
                ItemResponseEvent responseEvent = new ItemResponseEvent();
                responseEvent.setError(AppConstant.FAILURE_RESULT, t);
                EventBus.getDefault().post(responseEvent);
            }
        });
    }

    private void searchOrg(RequestEvent event){
        Log.i(TAG, "searchOrg request");
        mApi.searchOrg(event).enqueue(new Callback<OrganizationResponseEvent>() {
            @Override
            public void onResponse(Call<OrganizationResponseEvent> call, Response<OrganizationResponseEvent> response) {
                OrganizationResponseEvent responseEvent = response.body();
                if(response.isSuccessful()){
                    Log.i(TAG, "add searchOrg --> success");
                    EventBus.getDefault().post(responseEvent);
                }else{
                    Log.i(TAG, "add searchOrg --> fail");
                    EventBus.getDefault().post(responseEvent);
                }
            }

            @Override
            public void onFailure(Call<OrganizationResponseEvent> call, Throwable t) {
                Log.i(TAG, "add searchOrg --> onFailure: " + t.getMessage());
                OrganizationResponseEvent responseEvent = new OrganizationResponseEvent();
                responseEvent.setError(AppConstant.FAILURE_RESULT, t);
                EventBus.getDefault().post(responseEvent);
            }
        });
    }

    private void updatePassword(RequestEvent event){
        Log.i(TAG, "updatePassword request");
        mApi.updatePassword(event).enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                if(response.isSuccessful()){
                    Log.i(TAG, "updatePassword --> success");
                    EventBus.getDefault().post(appResponse);
                }
                else {
                    Log.i(TAG, "updatePassword --> fail");
                    EventBus.getDefault().post(appResponse);
                }
            }

            @Override
            public void onFailure(Call<AppResponse> call, Throwable t) {
                Log.i(TAG, "updatePassword --> onFailure: " + t.getMessage());
                AppResponse responseEvent = new AppResponse();
                responseEvent.setError(AppConstant.FAILURE_RESULT, t);
                EventBus.getDefault().post(responseEvent);
            }
        });
    }

    private void forgotPassword(RequestEvent event){
        Log.i(TAG, "forgotPassword request");
        mApi.forgotPassword(event).enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                if(response.isSuccessful()){
                    Log.i(TAG, "forgotPassword --> success");
                    EventBus.getDefault().post(appResponse);
                }
                else {
                    Log.i(TAG, "forgotPassword --> fail");
                    EventBus.getDefault().post(appResponse);
                }
            }

            @Override
            public void onFailure(Call<AppResponse> call, Throwable t) {
                Log.i(TAG, "forgotPassword --> onFailure: " + t.getMessage());
                AppResponse responseEvent = new AppResponse();
                responseEvent.setError(AppConstant.FAILURE_RESULT, t);
                EventBus.getDefault().post(responseEvent);
            }
        });
    }
}
