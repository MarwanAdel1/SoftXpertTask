package com.example.softxperttask;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.softxperttask.model.Car;
import com.example.softxperttask.ui.CarAdapter;
import com.example.softxperttask.ui.CarViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CarViewModel mCarViewModel;

    SwipeRefreshLayout pullToRefresh;
    RecyclerView carsRecyclerView;
    CarAdapter carsAdapter;
    List<Car> carsArrayList;

    int lastLoadedPage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCarViewModel = ViewModelProviders.of(this).get(CarViewModel.class);
        carsArrayList = new ArrayList<>();

        initializeViews();
        handleViewsActions();
        setupCarsRecyclerView();
        fetchCarsData(false);
        initScrollListener();

        mCarViewModel.carMutableLiveData.observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(List<Car> cars) {
                if (cars != null) {
                    handleActionsForRefreshSuccessFromPagination(cars);
                }
            }
        });

    }

    private void initializeViews() {
        carsRecyclerView = findViewById(R.id.cars_recycler);
        pullToRefresh = findViewById(R.id.pullToRefresh);
    }

    private void handleViewsActions() {
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchCarsData(false);
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    private void setupCarsRecyclerView() {
        carsAdapter = new CarAdapter();
        carsRecyclerView.setAdapter(carsAdapter);
        carsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initScrollListener() {
        carsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == carsArrayList.size() - 1) {
                    fetchCarsData(true);
                }
            }
        });
    }

    private void fetchCarsData(boolean forPagination) {
        if (forPagination) {
            mCarViewModel.getCarsFromRetrofit(lastLoadedPage);
        } else if (!forPagination) {
            carsArrayList.clear();
            lastLoadedPage = 1;
            mCarViewModel.getCarsFromRetrofit(lastLoadedPage);
        }
    }

    private void handleActionsForRefreshSuccessFromPagination(List<Car> carsList) {
        carsArrayList.addAll(carsList);
        carsAdapter.changeData(carsArrayList);
        lastLoadedPage++;
    }
}