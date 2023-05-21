package com.ipm.ipm.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.ipm.ipm.Model.Account;

public class SharedPreferencesHelper extends AppCompatActivity {
    final  String SHARE_PRE_NAME="Account";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARE_PRE_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveAccount(String email, String password) {
        editor.putString("email", email);
        editor.putString("password", password);
        editor.commit();
    }

    public Account getAccount(){
        String username = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");
        return new Account(username, password);
    }

    public void saveJwt(String jwt){
        editor.putString("jwt","Bearer " + jwt);
        editor.commit();
    }

    public String getJwt(){
        String jwt = sharedPreferences.getString("jwt", "");
        return jwt;
    }
}