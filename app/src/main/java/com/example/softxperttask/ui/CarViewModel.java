package com.example.softxperttask.ui;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.softxperttask.MainActivity;
import com.example.softxperttask.data.CarClients;
import com.example.softxperttask.model.Car;
import com.example.softxperttask.model.JsonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarViewModel extends ViewModel {
    public MutableLiveData <List<Car>> carMutableLiveData= new MutableLiveData<>();


    public void getCarsFromRetrofit(int page){
        CarClients.getInstance().getCars(page).enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if (response.isSuccessful()){
                    JsonResponse mJsonResponse= response.body();
                    carMutableLiveData.setValue(mJsonResponse.getCarList());
                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Log.e(MainActivity.class.getSimpleName(),"Hi : Error");
            }
        });
    }
}
