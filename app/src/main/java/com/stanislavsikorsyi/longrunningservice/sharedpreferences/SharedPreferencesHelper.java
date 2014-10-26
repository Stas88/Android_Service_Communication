package com.stanislavsikorsyi.longrunningservice.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.stanislavsikorsyi.longrunningservice.ContextAwareApplication;

/**
 * Created by stanislavsikorsyi on 25.09.14.
 */
public class SharedPreferencesHelper {

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor editor;
    private static SharedPreferencesHelper instance;

    private SharedPreferencesHelper() {
    }

    public SharedPreferencesHelper getInstance() {
        if (instance == null) {
            prefs = new ContextAwareApplication().getSharedPreferences(
                    "com.stanislavsikorsyi", Context.MODE_PRIVATE);
            editor = prefs.edit();
        }
        return instance;
    }

    public String get(String key) {
       return prefs.getString(key, "");
    }

    public void put(String key, String value) {
        editor.putString(key, value).apply();
    }
}


