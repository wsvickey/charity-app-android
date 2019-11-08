package com.tech.agape4charity.object;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * Created by Charitha Ratnayake on 6/5/2018.
 */

public class AppResponse {

    private int code;
    private Boolean status;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Boolean isSuccess() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AppResponse{" +
                "code=" + code +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }

    public void setError(int result, Throwable throwable) {
        code = result;
        status = false;

        if(throwable instanceof SocketTimeoutException){
            this.message = "Server error please try again later";
        }else if(throwable instanceof ConnectException){
            this.message = "Please check your internet connection and try again!";
        }else{
            this.message = message;
        }
    }
}
