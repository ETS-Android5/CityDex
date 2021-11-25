package com.tlbail.ptuts3androidapp.View.Achievement;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
    private Achievement achievement;

    public AchievementViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        imageView = itemView.findViewById(R.id.achivementImageView);
        textView = itemView.findViewById(R.id.descriptionAchivement);
        constraintLayout = itemView.findViewById(R.id.constraintLayoutWhichHidePhotoAchievement);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateViewHolder(Achievement achievement){
        this.achievement = achievement;
        this.textView.setText(achievement.getName());
        imageView.setImageResource(achievement.getDrawableId());
        if(!achievement.isUnlocked()){
            constraintLayout.setBackgroundColor(Color.argb(100,255,255,255));
        }else{
            constraintLayout.setBackgroundColor(Color.argb(0,255,255,255));
            textView.setTextColor(Color.BLACK);

        }
    }


    public void showPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
        LayoutInflater layoutInflater = (LayoutInflater) itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View customView = layoutInflater.inflate(R.layout.layout_achievementdetail, null);
        // reference the textview of custom_popup_dialog
        TextView tv = (TextView) customView.findViewById(R.id.textviewachievementdetail);
        TextView descriptionTextView = (TextView) customView.findViewById(R.id.textviewDescriptionAchievement);


        tv.setText(achievement.getName());
        descriptionTextView.setText(achievement.getDescription());


        builder.setView(customView);
        AlertDialog alertDialog = builder.create();
        builder.show();

    }
}
