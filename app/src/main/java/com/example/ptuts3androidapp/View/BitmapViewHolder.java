package com.example.ptuts3androidapp.View;

import android.graphics.Bitmap;
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

public class BitmapViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private View itemView;

    public BitmapViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        imageView = itemView.findViewById(R.id.imageView4);
    }


    public void updateViewHolder(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }


}