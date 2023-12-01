package ru.businesscloud.vin39.auth;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AuthHelper {
    static private SharedPreferences mSettings;

    static public void setSid(String sid) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("sid", sid);
        editor.apply();
    }

    static public void setMqtt(boolean has) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean("mqtt", has);
        editor.apply();
    }

    static public void setRoot(boolean isRoot) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean("root", isRoot);
        editor.apply();
    }

    static public boolean getRoot() {
        return mSettings.getBoolean("root", false);
    }

    static public boolean getMqtt() {
        return mSettings.getBoolean("mqtt", false);
    }

    static public String getSid() {
        return mSettings.getString("sid", "");
    }

    static public boolean isLogin() {
        String sid = mSettings.getString("sid", "");
        if (sid != null && !sid.isEmpty()) return true;
        else return false;
    }

    static public void create(Activity mainActivity) {
        mSettings = mainActivity.getSharedPreferences("setting", Context.MODE_PRIVATE);
    }
}
