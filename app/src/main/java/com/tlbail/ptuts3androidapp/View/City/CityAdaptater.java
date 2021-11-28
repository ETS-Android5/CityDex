package com.tlbail.ptuts3androidapp.View.City;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tlbail.ptuts3androidapp.Model.City.City;
import com.tlbail.ptuts3androidapp.Model.City.CityData;
import com.tlbail.ptuts3androidapp.R;

import java.util.List;

public class CityAdaptater extends RecyclerView.Adapter<CityViewHolder> {


    private List<City> cities;
    private RecyclerView recyclerView;

    public CityAdaptater(List<City> cities, RecyclerView recyclerView) {
        super();
        this.cities = cities;
        this.recyclerView = recyclerView;
    }

    public List<City> getCities() {
        return cities;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cityviewholder, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        holder.updateViewHolder(cities.get(position));
        holder.getItemView().setMinimumHeight(recyclerView.getHeight()/7);
        Log.e(recyclerView.getHeight()+"", recyclerView.getHeight()/7+"");
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, new RecyclerView.State(), holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}
