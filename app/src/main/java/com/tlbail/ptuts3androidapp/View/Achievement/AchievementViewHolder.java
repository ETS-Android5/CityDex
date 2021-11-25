package com.tlbail.ptuts3androidapp.View.Achievement;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tlbail.ptuts3androidapp.Model.Achievement.Achievement;
import com.tlbail.ptuts3androidapp.R;

public class AchievementViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;
    private ImageView imageView;
    private View itemView;
    private ConstraintLayout constraintLayout;

    public AchievementViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        imageView = itemView.findViewById(R.id.achivementImageView);
        textView = itemView.findViewById(R.id.descriptionAchivement);
        constraintLayout = itemView.findViewById(R.id.constraintLayoutWhichHidePhotoAchievement);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateViewHolder(Achievement achievement){
        this.textView.setText(achievement.getName());
        imageView.setImageResource(achievement.getDrawableId());
        if(!achievement.isUnlocked()){
            constraintLayout.setBackgroundColor(Color.argb(100,255,255,255));
        }else{
            constraintLayout.setBackgroundColor(Color.argb(0,255,255,255));
        }
    }


}
