package com.tlbail.ptuts3androidapp.View.Achievement;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tlbail.ptuts3androidapp.Model.Achievement.Achievement;
import com.tlbail.ptuts3androidapp.R;

public class AchievementViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;
    private ImageView imageView;
    private View itemView;

    public AchievementViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        imageView = itemView.findViewById(R.id.achivementImageView);
        textView = itemView.findViewById(R.id.descriptionAchivement);

    }


    public void updateViewHolder(Achievement achievement){
        this.textView.setText(achievement.getName());
        imageView.setImageResource(achievement.getDrawableId());

    }


}
