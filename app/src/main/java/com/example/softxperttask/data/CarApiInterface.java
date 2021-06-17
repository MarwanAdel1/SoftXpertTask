package com.example.softxperttask.data;

import com.example.softxperttask.model.JsonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CarApiInterface {
    @GET("cars")
    Call<JsonResponse> getCars(@Query("page") int page);
}
