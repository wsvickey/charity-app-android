package com.tech.agape4charity.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Charitha Ratnayake on 6/5/2018.
 */

public class LoginResponseEvent extends AppResponse{
    @SerializedName("data")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "LoginResponseEvent{" +
                "user=" + user.toString() +
                '}';
    }
}
