package com.tlbail.ptuts3androidapp.View.BackgroundOfPhoto;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptuts3androidapp.R;

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