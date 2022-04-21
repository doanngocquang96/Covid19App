package com.example.applicationcv;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

public class UserSessionManager {

    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "userAccount";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // All Shared Preferences Keys
    private static final String IS_USER_INFO = "IsUserInfo";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    //..some var more info (make variable public to access from outside)
    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_SEX = "sex";
    public static final String KEY_BIRTH = "birth";
    public static final String KEY_IDCARD = "idCard";
    public static final String KEY_IDBHYT = "idBHYT";
    public static final String KEY_NATIONAL = "national";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_NUMPHONE = "numPhone";
    public static final String KEY_VACCINE = "vaccine";

    // Constructor
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String name, String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        Log.i("tag", "saved User");
        // commit changes
        editor.commit();
    }

    //Create more info session
    public void createUserInfoSession(String fullName, String sex, String birth, String idCard, String idBHYT, String national, String address, String numPhone, String vaccine){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_INFO, true);

        // Storing info in pref
        editor.putString(KEY_FULLNAME, fullName)
        .putString(KEY_SEX, sex)
        .putString(KEY_BIRTH, birth)
        .putString(KEY_IDCARD, idCard)
        .putString(KEY_IDBHYT, idBHYT)
        .putString(KEY_NATIONAL, national)
        .putString(KEY_ADDRESS, address)
        .putString(KEY_NUMPHONE, numPhone)
        .putString(KEY_VACCINE, vaccine);

        // commit changes
        editor.commit();
    }


    public boolean checkLogin(){
        // Check login status !this.isUserLoggedIn()
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, loginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        checkInfo();
        return false;
    }


    public boolean checkInfo(){

        if(!this.isUserInfo()){

            Intent i = new Intent(_context, getInfoUser.class);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            _context.startActivity(i);

            return true;
        }
        return false;
    }


    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user info
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_FULLNAME, pref.getString(KEY_FULLNAME, null));
        user.put(KEY_SEX, pref.getString(KEY_SEX, null));
        user.put(KEY_BIRTH, pref.getString(KEY_BIRTH, null));
        user.put(KEY_IDCARD, pref.getString(KEY_IDCARD, null));
        user.put(KEY_IDBHYT, pref.getString(KEY_IDBHYT, null));
        user.put(KEY_NATIONAL, pref.getString(KEY_NATIONAL, null));
        user.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));
        user.put(KEY_NUMPHONE, pref.getString(KEY_NUMPHONE, null));
        user.put(KEY_VACCINE, pref.getString(KEY_VACCINE, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, MainActivity2.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    // Check for info
    public boolean isUserInfo(){
        return pref.getBoolean(IS_USER_INFO, false);
    }
}
