package com.tlbail.ptuts3androidapp.View.BackgroundOfPhoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptuts3androidapp.R;

import java.util.List;

public class BackgroundViewAdapter extends RecyclerView.Adapter<BitmapViewHolder> {


    private List<Bitmap> bitmaps;

    public BackgroundViewAdapter(List<Bitmap> bitmaps) {
        super();
        this.bitmaps = bitmaps;
    }

    @NonNull
    @Override
    public BitmapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.imageviewinframelayout, parent, false);
        return new BitmapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BitmapViewHolder holder, int position) {
        holder.updateViewHolder(bitmaps.get(position));
    }


    @Override
    public int getItemCount() {
        return bitmaps.size();
    }
}
