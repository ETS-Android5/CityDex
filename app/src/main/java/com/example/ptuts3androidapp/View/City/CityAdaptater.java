package com.example.ptuts3androidapp.View.City;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptuts3androidapp.Model.City.City;
import com.example.ptuts3androidapp.R;

import java.util.List;

public class CityAdaptater extends RecyclerView.Adapter<CityViewHolder> {


    private List<City> cities;

    public CityAdaptater(List<City> cities) {
        super();
        this.cities = cities;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //Todo rajouter le bon layout   View view = inflater.inflate(R.layout."id du layout a remplacer", parent, false);
        return null; //new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        holder.updateViewHolder(cities.get(position));
    }


    @Override
    public int getItemCount() {
        return cities.size();
    }
}
