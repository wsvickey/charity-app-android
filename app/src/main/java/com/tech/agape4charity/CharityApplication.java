package com.tech.agape4charity;

import android.app.Application;
import android.content.Context;

import com.tech.agape4charity.service.Controller;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Charitha Ratnayake on 6/5/2018.
 */

public class CharityApplication extends Application {
    private static CharityApplication mInstance;
    private Controller mController;

    public static CharityApplication getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CharityApplication();
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (mController==null){
            mController = new Controller(this);
        }
        EventBus.getDefault().register(mController);
    }
}
