package com.doodeec.weather.android;

import android.app.Application;
import android.content.Context;

/**
 * @author Dusan Bartos
 */
public class WedrApplication extends Application {

    private static WedrApplication mInstance;

    public WedrApplication() {
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // force AsyncTask to be initialized in the main thread due to the bug:
        // http://stackoverflow.com/questions/4280330/onpostexecute-not-being-called-in-asynctask-handler-runtime-exception
        try {
            Class.forName("android.os.AsyncTask");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Context getContext() {
        return mInstance;
    }
}
