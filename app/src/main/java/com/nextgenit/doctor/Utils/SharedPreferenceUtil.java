package com.nextgenit.doctor.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    private static final String mSharedName = "premo_prefs";
    public static final String TYPE_TOKEN = "token";
    public static final String TYPE_USER_ID = "userid";
    public static final String  USER_ID = "user";
    public static final String  PHARMACY_ID = "pharmacy_id";
    public static final String TYPE_USER_NAME = "username";
    public static final String TYPE_ADMIN = "username";
    public static final String USER_PIC = "pic";
    public static final String USER_EMAIL = "email";
    public static final String DATA = "data";
    public static final String USER_ROLE = "role";
    public static final String SYNC= "sync";
    public static final String LANG= "lang";
    public static void saveShared(Context c, String type, String val) {
        SharedPreferences.Editor ed = c.getSharedPreferences(mSharedName, Context.MODE_PRIVATE).edit();
        ed.putString(type, val);
        ed.commit();
    }


    public static void clearUserData(Context c) {
        SharedPreferences.Editor ed = c.getSharedPreferences(mSharedName, Context.MODE_PRIVATE).edit();
        //  ed.remove(TYPE_USER_LOGIN);

        ed.commit();

        SharedPreferences.Editor ed1 = c.getSharedPreferences(mSharedName, Context.MODE_PRIVATE).edit();
        ed1.clear();
        ed1.commit();
    }

    public static void removeShared(Context c, String type) {
        SharedPreferences.Editor ed = c.getSharedPreferences(mSharedName, Context.MODE_PRIVATE).edit();
        ed.remove(type);
        ed.commit();
    }

    public static String getShared(Context c, String type) {
        return c.getSharedPreferences(mSharedName, Context.MODE_PRIVATE).getString(type, "");
    }
    public static String getPic(Context c) {
        String val = getShared(c, USER_PIC);
        return val;
    }
    public static String getData(Context c) {
        String val = getShared(c, DATA);
        return val;
    }
    public static String getUserID(Context c) {
        String val = getShared(c, TYPE_USER_ID);
        return val;
    }public static String getUserName(Context c) {
        String val = getShared(c, TYPE_USER_NAME);
        return val;
    }
    public static String getPharmacyId(Context c) {
        String val = getShared(c, PHARMACY_ID);
        return val;
    }
    public static String getAdmin(Context c) {
        String val = getShared(c, TYPE_ADMIN);
        return val;
    }
    public static String getUser(Context c) {
        String val = getShared(c, USER_ID);
        return val;
    }
    public static String getSync(Context c) {
        String val = getShared(c, SYNC);
        return val;
    }
    public static String getUserRole(Context c) {
        String val = getShared(c, USER_ROLE);
        return val;
    }
}
