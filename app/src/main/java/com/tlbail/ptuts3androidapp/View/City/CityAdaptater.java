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

import com.tlbail.ptuts3androidapp.Model.CityApi.City;
import com.tlbail.ptuts3androidapp.R;

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
        View view = inflater.inflate(R.layout.cityviewholder, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        holder.updateViewHolder(cities.get(position));
    }

    @Override
    public void onViewRecycled(@NonNull CityViewHolder holder) {
        super.onViewRecycled(holder);
    }



    @Override
    public int getItemCount() {
        return cities.size();
    }
}
