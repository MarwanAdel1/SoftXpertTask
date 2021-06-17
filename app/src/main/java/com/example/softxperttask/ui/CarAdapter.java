package com.example.softxperttask.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softxperttask.R;
import com.example.softxperttask.model.Car;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    private List<Car> cars = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_data_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        populateItemRows(holder, position);
    }

    @Override
    public int getItemCount() {
        if (cars == null) return 0;
        return cars.size();
    }

    private void populateItemRows(ViewHolder viewHolder, int position) {
        Car car = cars.get(position);

        viewHolder.carBrandName.setText(car.getBrand());
        viewHolder.carIsUsed.setText(String.valueOf(car.isUsed()));
        viewHolder.carConstructionYear.setText(car.getConstructionYear());
        if (car.getImageUrl() == null) {
            viewHolder.carImage.setImageResource(R.drawable.car_model_default);
        } else {
            Picasso.get().load(car.getImageUrl()).into(viewHolder.carImage);
        }

        if (car.isUsed()) {
            viewHolder.carIsUsed.setTextColor(Color.GREEN);
        } else {
            viewHolder.carIsUsed.setTextColor(Color.BLUE);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView carBrandName, carIsUsed, carConstructionYear;
        ImageView carImage;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);

            carBrandName = itemView.findViewById(R.id.car_brand_textView);
            carIsUsed = itemView.findViewById(R.id.car_isused_textView);
            carConstructionYear = itemView.findViewById(R.id.car_construction_year_textView);
            carImage = itemView.findViewById(R.id.car_image);
        }
    }

    public void changeData(List<Car> carsList) {
        this.cars = carsList;
        notifyDataSetChanged();
    }
}
