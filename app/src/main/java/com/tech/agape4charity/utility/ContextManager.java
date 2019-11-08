package com.tech.agape4charity.utility;

import android.app.Application;

import java.lang.reflect.Method;

/**
 * Created by JacH on 04/04/17.
 */

public class ContextManager {
    private static final String THREAD_NAME = "android.app.ActivityThread";
    private static final String METHOD_NAME = "currentApplication";

    private static Application context;

    /**
     * This method return current context of the app
     * @return Application
     */
    public static Application getInstance() {

        if (context != null) {
            return context;
        }

        try {
            final Class<?> activityThread = Class.forName(THREAD_NAME);
            final Method method = activityThread.getMethod(METHOD_NAME);
            context = (Application) method.invoke(null, (Object[]) null);
        } catch (final Exception e) {
            throw new RuntimeException("Failed to get application instance.");
        }

        return context;
    }
}
