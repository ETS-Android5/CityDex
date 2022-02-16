package com.tlbail.ptuts3androidapp.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;

import com.tlbail.ptuts3androidapp.Model.Photo.Photo;
import com.tlbail.ptuts3androidapp.Model.Photo.PhotoManager;
import com.tlbail.ptuts3androidapp.R;

public class PhotoActivity extends AppCompatActivity {

    public static final String URIBITMAPKEY = "bitmap";
    private ConstraintLayout constraintLayout;
    private boolean isloading;
    private PhotoManager photoManager;
    private View fragmentPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoManager = new PhotoManager(this);
        setContentView(R.layout.activity_photo);
        bindUI();
    }

    private void bindUI() {
        fragmentPhoto = findViewById(R.id.fragmentPhotoContainerView);
        constraintLayout = findViewById(R.id.rootactivityPhotoConstraintLayout);
        findViewById(R.id.takePhotoImageButton).setOnClickListener(v -> takePhoto());
        findViewById(R.id.b_galeri2).setOnClickListener(v -> startIntent(CollectionActivity.class));
    }

    private void takePhoto() {
        if(isloading) return;
        addLoadingLayout();
        new Thread(new Runnable() {
            @Override
            public void run() {
                startResultActivityWithPhotoInfo();
            }
        }).start();
        isloading = true;
    }

    public void startIntent(Class className){
        Intent activityIntent = new Intent(this, className);
        this.startActivity(activityIntent);
    }

    private void startResultActivityWithPhotoInfo() {
        Uri resultedUriBitmap = getUriBitmapOfFragment();
        Intent activityIntent = new Intent(this, ResultActivity.class);
        activityIntent.putExtra(URIBITMAPKEY, resultedUriBitmap.toString());
        this.startActivity(activityIntent);

    }

    private Uri getUriBitmapOfFragment() {
        TextureView textureView = fragmentPhoto.findViewById(R.id.photoPreviewTextureView);
        Bitmap bitmap = textureView.getBitmap();
        Photo photoToReturn = photoManager.createPhotoFromBitmap(bitmap);
        return photoToReturn.getPhotoUri();
    }

    private void addLoadingLayout() {
        LayoutInflater inflater = (LayoutInflater)   getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.layout_photoresult, null);
        constraintLayout.addView(child);
        ConstraintSet set = new ConstraintSet();
        set.clone(constraintLayout);
        set.connect(child.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 0);
        set.connect(child.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT, 0);
        set.applyTo(constraintLayout);
    }

    @Override
    public void onBackPressed() {
        Intent activityIntent = new Intent(PhotoActivity.this, HomeActivity.class);
        PhotoActivity.this.startActivity(activityIntent);
    }
}