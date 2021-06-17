package com.example.softxperttask.model;

import java.util.List;

public class JsonResponse {
    private List<Car> data ;
    private int status;

    public List<Car> getCarList() {
        return data;
    }

    public void setCarList(List<Car> carList) {
        this.data = carList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
