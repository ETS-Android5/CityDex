package com.tlbail.ptuts3androidapp.View.Achievement;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.tlbail.ptuts3androidapp.Model.Achievement.Achievement;
import com.tlbail.ptuts3androidapp.R;

import java.util.List;

public class AchievementAdaptater extends RecyclerView.Adapter<AchievementViewHolder> {


    private List<Achievement> achievements;

    public AchievementAdaptater(List<Achievement> achievements) {
        super();
        this.achievements = achievements;
    }

    @NonNull
    @Override
    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.archivemenviewholder, parent, false);
        return new AchievementViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
        holder.updateViewHolder(achievements.get(position));
    }


    @Override
    public int getItemCount() {
        return achievements.size();
    }
}
