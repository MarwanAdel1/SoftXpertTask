package com.example.softxperttask.data;

import com.example.softxperttask.model.JsonResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CarClients {
    private static final String BASE_URL = "https://demo1585915.mockable.io/api/v1/";
    private CarApiInterface mCarApiInterface;
    private static CarClients INSTANCE;

    public CarClients() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mCarApiInterface = retrofit.create(CarApiInterface.class);
    }

    public static CarClients getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CarClients();
        }
        return INSTANCE;
    }

    public Call<JsonResponse> getCars(int page) {
        Call<JsonResponse> call = mCarApiInterface.getCars(page);
        return call;
    }
}
