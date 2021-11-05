package com.example.ptuts3androidapp.Model.Photo;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.example.ptuts3androidapp.Model.User.User;

import java.io.IOException;

public class PhotoManager {

    private static final String PHOTO_URI_PROPERTI = "photoUri";

    private AppCompatActivity appCompatActivity;
    private Context context;
    private PhotoTaker photoTaker;
    private User user;

    public PhotoManager(AppCompatActivity appCompatActivity, User user){
        this.appCompatActivity = appCompatActivity;
        this.user = user;
        this.context = appCompatActivity.getApplicationContext();
        photoTaker = new PhotoTaker(appCompatActivity);
    }

    public PhotoManager(AppCompatActivity appCompatActivity){
        this(appCompatActivity, new User(new UserPropertyLocalLoader(appCompatActivity), new com.example.ptuts3androidapp.Model.City.CityLocalLoader(appCompatActivity)));
    }
    
    public void takePhoto(){
        photoTaker.takePhoto();
    }


    public void storeLastPhotoPathTakedInLocalStorage(){
        if(photoTaker.photoUri == null) return;
        user.setProperty(PHOTO_URI_PROPERTI, photoTaker.photoUri.toString());
    }

    public void loadLastPhotoTakedFromLocalStorage(){
        photoTaker.photoUri = Uri.parse(user.get(PHOTO_URI_PROPERTI));
    }

    public Uri getPhotoUri(){
        return photoTaker.photoUri;
    }


    public void loadPhotoIntoImageView(ImageView imageView){
        try {
            imageView.setImageBitmap(MediaStore.Images.Media.getBitmap(appCompatActivity.getContentResolver(), photoTaker.photoUri));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
