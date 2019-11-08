package com.tech.agape4charity;

import com.tech.agape4charity.object.User;
import com.tech.agape4charity.utility.ContextManager;
import com.tech.agape4charity.utility.TinyDB;

/**
 * Created by Charitha Ratnayake on 6/5/2018.
 */

public class SessionManager {
    private static SessionManager mInstance;
    private TinyDB db = new TinyDB(ContextManager.getInstance());

    public static SessionManager getInstance() {
        if (mInstance == null) {
            mInstance = new SessionManager();
        }
        return mInstance;
    }


    public void setLoginStatus(boolean loginStatus) {
        db.putBoolean(AppConstant.Preference.KEY_IS_LOGGED_IN, loginStatus);
    }


    public Boolean isLoggedIn() {
        return db.getBoolean(AppConstant.Preference.KEY_IS_LOGGED_IN);
    }

    public void setUser(User user) {
        db.putObject(AppConstant.Preference.KEY_USER_OBJ, user);
    }

    public User getUser() {
        return db.getObject(AppConstant.Preference.KEY_USER_OBJ, User.class);
    }

    public void logout() {
        setLoginStatus(false);
        setUser(null);
    }
}
