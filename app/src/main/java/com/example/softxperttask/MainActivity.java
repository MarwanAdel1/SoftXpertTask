package com.example.softxperttask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

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

    RecyclerView carsRecyclerView;
    CarAdapter carsAdapter;
    SwipeRefreshLayout pullToRefresh;
    List<Car> carsArrayList = new ArrayList<>();

    int lastLoadedPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCarViewModel = ViewModelProviders.of(this).get(CarViewModel.class);

        initializeViews();
        handleViewsActions();
        setupCarsRecyclerView();
        fetchCarsData(false);
        initScrollListener();

        mCarViewModel.carMutableLiveData.observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(List<Car> cars) {
                carsArrayList=cars;
                Log.e(CarViewModel.class.getSimpleName(),"Hi : "+carsArrayList.size());
                fetchCarsData(true);
            }
        });

    }

    void initializeViews() {
        carsRecyclerView = findViewById(R.id.cars_recycler);
        pullToRefresh = findViewById(R.id.pullToRefresh);
    }

    void handleViewsActions() {
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchCarsData(false);
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    private void setupCarsRecyclerView() {
        carsAdapter = new CarAdapter(carsArrayList);
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
                    //bottom of list!
                    loadMore();

                }
            }
        });
    }

    private void fetchCarsData(boolean forPagination) {
        if (forPagination) {
            handleActionsForRefreshSuccessFromPagination(carsArrayList);
        } else {
            handleActionsForRefreshSuccessFromRefresh(carsArrayList);
        }
    }

    private void handleActionsForRefreshSuccessFromPagination(List<Car> carsList) {
        //removeLoadingItemFromAdapter();
        carsArrayList.addAll(carsList);
        carsAdapter.changeData(carsArrayList);
        lastLoadedPage += 1;
        mCarViewModel.getCarsFromRetrofit(lastLoadedPage);
    }

    private void handleActionsForRefreshSuccessFromRefresh(List<Car> carsList) {
        carsArrayList = carsList;
        lastLoadedPage = 1;
        carsAdapter.changeData(carsList);
        mCarViewModel.getCarsFromRetrofit(lastLoadedPage);
    }

    private void loadMore() {
        carsArrayList.add(null);
        carsAdapter.notifyItemInserted(carsArrayList.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchCarsData(true);
            }
        }, 2000);
    }
}