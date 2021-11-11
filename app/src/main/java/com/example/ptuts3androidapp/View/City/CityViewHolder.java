package com.example.ptuts3androidapp.View.City;

import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptuts3androidapp.Model.City.City;
import com.example.ptuts3androidapp.Model.City.CityData;
import com.example.ptuts3androidapp.R;

import java.io.IOException;

public class CityViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;
    private ImageView imageView;
    private View itemView;

    public CityViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        textView = itemView.findViewById(R.id.cityHolderTextView);
        imageView = itemView.findViewById(R.id.imageCityHolder);
        imageView.setImageResource(R.drawable.polish_cow);
    }


    public void updateViewHolder(City city){
        this.textView.setText(city.getCityData().getCityProperty().get(CityData.CITY_NAME_KEY) +  city.toString());
        if(city.getPhoto().getPhotoUri().toString().toLowerCase() == "") return;
        try {
            imageView.setImageBitmap(MediaStore.Images.Media.getBitmap(itemView.getContext().getContentResolver(),  city.getPhoto().getPhotoUri()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
