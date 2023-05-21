package com.ipm.ipm.Helper;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

public class ErrorHelper {
    public static void toastError(Context context, ResponseBody response){
        String err="";
        try {
            JSONObject jsonObject= new JSONObject(response.string());
            err= jsonObject.getString("message");
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "Có lỗi xảy ra: "+ err, Toast.LENGTH_SHORT).show();
    }
}
