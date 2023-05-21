package com.ipm.ipm.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ipm.ipm.Model.Account;
import com.ipm.ipm.Model.Contract;
import com.ipm.ipm.Model.Industrial;
import com.ipm.ipm.Model.Request.ChangePasswordRequest;
import com.ipm.ipm.Model.Request.CreateContractRequest;
import com.ipm.ipm.Model.Response.FactoriesResponse;
import com.ipm.ipm.Model.Response.IndustrialResponse;
import com.ipm.ipm.Model.Response.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiController {
    String DOMAIN = "https://industrial.gorokiapp.com/api/";
    Gson gson =new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setLenient().create();

    ApiController apiService = new Retrofit.Builder()
            .baseUrl(DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiController.class);
    //account
    @POST("accounts/login")
    Call<LoginResponse> login(@Body() Account account);

    @GET("accounts/profile")
    Call<Account> getProfile(@Header("Authorization") String jwt);

    @PATCH("accounts")
    Call<Account> updateProfile(@Header("Authorization") String jwt, @Body() Account account);

    @PATCH("accounts")
    Call<Account> changePassword(@Header("Authorization") String jwt, @Body() ChangePasswordRequest passwordRequest);

    //industrials
    @GET("industrials")
    Call<IndustrialResponse> getIndustrials(@Query("page") int page);

    @GET("industrials/{id}")
    Call<Industrial> getIndustrialById(@Path("id") String id);

    @GET("industrials")
    Call<IndustrialResponse> searchIndustrial(@Query("search") String search);

    //factories
    @GET("factories/industrial/{id}")
    Call<FactoriesResponse> getFactoriesOfIndustrial(@Path("id") String id, @Query("page") int page);

    @GET("factories/industrial/{id}")
    Call<FactoriesResponse> searchFactories(@Path("id") String id, @Query("search") String search);



    //contract
    @POST("contracts")
    Call<Contract> createContract(@Header("Authorization") String jwt, @Body() CreateContractRequest contract);
}
