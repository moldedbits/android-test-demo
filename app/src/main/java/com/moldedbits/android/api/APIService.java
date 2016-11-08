package com.moldedbits.android.api;

import com.moldedbits.android.models.request.LoginRequest;
import com.moldedbits.android.models.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {

    @POST("users/sign_in.json")
    Call<LoginResponse> login(@Body LoginRequest request);
}
