package com.tech.agape4charity.service;

import com.tech.agape4charity.object.AddToMyListResponseEvent;
import com.tech.agape4charity.object.AppResponse;
import com.tech.agape4charity.object.CategoryResponseEvent;
import com.tech.agape4charity.object.ItemResponseEvent;
import com.tech.agape4charity.object.LoginResponseEvent;
import com.tech.agape4charity.object.OrganizationResponseEvent;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Charitha Ratnayake on 2/15/2018.
 */

public interface CharityApi {
    @POST("/api/signin/")
    Call<LoginResponseEvent> signin(@Body RequestEvent requestEvent);
    @POST("/api/signup/")
    Call<LoginResponseEvent> signup(@Body RequestEvent requestEvent);
    @POST("/api/getList/")
    Call<OrganizationResponseEvent> getMyList(@Body RequestEvent requestEvent);
    @POST("/api/getCategories/")
    Call<CategoryResponseEvent> getCategories(@Body RequestEvent requestEvent);
    @POST("/api/getOrganizations/")
    Call<OrganizationResponseEvent> getOrganizations(@Body RequestEvent requestEvent);
    @POST("/api/addEvent/")
    Call<AddToMyListResponseEvent> addToMyList(@Body RequestEvent requestEvent);
    @POST("/api/getSearchOrganization/")
    Call<OrganizationResponseEvent> search(@Body RequestEvent requestEvent);
    @POST("/api/updateNote/")
    Call<AppResponse> addNote(@Body RequestEvent requestEvent);
    @POST("/api/updateDate/")
    Call<AppResponse> updateDate(@Body RequestEvent requestEvent);
    @POST("/api/getReminder/")
    Call<ItemResponseEvent> getReminder(@Body RequestEvent requestEvent);
    @POST("/api/getOrg/")
    Call<OrganizationResponseEvent> searchOrg(@Body RequestEvent requestEvent);
    @POST("/api/changePassword/")
    Call<AppResponse> updatePassword(@Body RequestEvent requestEvent);
    @POST("/api/forgotPassword/")
    Call<AppResponse> forgotPassword(@Body RequestEvent requestEvent);

}
