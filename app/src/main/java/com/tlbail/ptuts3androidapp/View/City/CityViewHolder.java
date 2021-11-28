package com.tlbail.ptuts3androidapp.View.City;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tlbail.ptuts3androidapp.Model.City.City;
import com.tlbail.ptuts3androidapp.Model.City.CityData;
import com.tlbail.ptuts3androidapp.R;

public class CityViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;
    private ImageView imageView;
    private View itemView;

    public CityViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        textView = itemView.findViewById(R.id.cityText);
        imageView = itemView.findViewById(R.id.citySign);
        //TODO set image in imageView
    }


    public void updateViewHolder(City city){
        this.textView.setText(city.getCityData().getName());
    }

    public View getItemView() {
        return itemView;
    }
}
