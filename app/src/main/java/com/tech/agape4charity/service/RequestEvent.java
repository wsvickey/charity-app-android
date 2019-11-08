package com.tech.agape4charity.service;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Charitha Ratnayake on 6/5/2018.
 */

public class RequestEvent {
    @SerializedName("user_id")
    private long userId;
    @SerializedName("event_id")
    private long eventId;
    private long id;
    @SerializedName("organization_id")
    private long orgId;
    @SerializedName("donated_time")
    private long donatedTime;
    private String username;
    private String email;
    private String phone;
    private String address;
    private String password;
    @SerializedName("current_password")
    private String currentPassword;
    private String name;
    private String note;
    private String tag;
    private String location;
    @SerializedName("date_time")
    private long dateTime;
    @SerializedName("is_active")
    private Boolean isActive;
    private String status;

    public RequestEvent(String name, String email, String phone, String address, String password, RequestType type) {
        this.username = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.type = type;
    }

    public RequestEvent(long id, RequestType type) {
        this.id = id;
        this.type = type;
    }

    public RequestEvent(long id, long dateTime, RequestType type) {
        this.id = id;
        this.dateTime = dateTime;
        this.type = type;
    }

    public RequestEvent(String name, RequestType type) {
        this.name = name;
        this.type = type;
    }


    public RequestEvent(RequestType type) {
        this.type = type;
    }

    public RequestEvent(long userId, long orgId,long dateTime, String note, String status, String location,long id, RequestType type) {
        this.userId = userId;
        this.orgId = orgId;
        this.dateTime = dateTime;
        this.status = status;
        this.location = location;
        this.note = note;
        this.type = type;
        this.id = id;
    }

    public RequestType getType() {
        return type;
    }

    private RequestType type;

    public RequestEvent(String email, String password, RequestType type) {
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public RequestEvent(long id, String tag, RequestType type) {//note also same req
        this.id = id;
        this.tag = tag;
        this.type = type;
    }

    public RequestEvent(long userId, String password, String currentPassword, RequestType type) {
        this.userId = userId;
        this.password = password;
        this.currentPassword = currentPassword;
        this.type = type;
    }


    public enum RequestType{
        REGISTER, LOGIN, MY_LIST, GET_CATEGORIES, GET_ORGANIZATION_FROM_CAT, ADD_TO_MY_LIST, SEARCH, ADD_NOTE, UPDATE_DATE, GET_REMINDER,
        SEARCH_ORG,UPDATE_PASSWORD,FORGOT_PASSWORD;
    }
}
