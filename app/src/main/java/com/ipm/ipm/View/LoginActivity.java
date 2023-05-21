package com.ipm.ipm.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ipm.ipm.Controller.ApiController;
import com.ipm.ipm.Helper.SharedPreferencesHelper;
import com.ipm.ipm.Model.Account;
import com.ipm.ipm.Model.Response.LoginResponse;
import com.ipm.ipm.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edtEmail, edtPassword;
    Button btnSignIn;
    TextView tvSignUp;
    ImageView imgEye;
    CheckBox cbSaveAccount;

    SharedPreferencesHelper sharedPreferencesHelper;

    public boolean canLookPassword = false;
    public static String jwt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        sharedPreferencesHelper = new SharedPreferencesHelper(LoginActivity.this);
        refView();
        Account account = sharedPreferencesHelper.getAccount();
        edtEmail.setText(account.getEmail());
        edtPassword.setText(account.getPassword());

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                ApiController.apiService.login(new Account(email, password)).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()){
                            jwt = response.body().getJwt();
                            if (cbSaveAccount.isChecked()){
                                sharedPreferencesHelper.saveJwt(jwt);
                                sharedPreferencesHelper.saveAccount(email, password);
                            }
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else {
                            String err="";
                            try {
                                JSONObject jsonObject= new JSONObject(response.errorBody().string());
                                err= jsonObject.getString("message");
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(LoginActivity.this, "Có lỗi xảy ra: "+ err, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        System.out.println(t.getMessage());
                        Toast.makeText(LoginActivity.this, "Lỗi server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        imgEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canLookPassword){
                    canLookPassword = true;
                    imgEye.setImageResource(R.drawable.dis_eye);
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    canLookPassword = false;
                    imgEye.setImageResource(R.drawable.eye);
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void refView() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvSignUp = findViewById(R.id.tvSignUp);
        imgEye = findViewById(R.id.imgEye);
        cbSaveAccount = findViewById(R.id.cbSaveAccount);
    }
}