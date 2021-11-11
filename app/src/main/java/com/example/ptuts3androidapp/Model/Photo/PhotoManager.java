package com.example.ptuts3androidapp.Model.Photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoManager {

    private AppCompatActivity appCompatActivity;


    public PhotoManager(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    public Photo createPhotoObjectFromBitmap(Bitmap bitmap){
        File createimagefile = createImageFile();
        writeBitmapToFile(bitmap, createimagefile);
        Photo photo = new Photo(Uri.fromFile(createimagefile), null);
        return photo;
    }

    private void writeBitmapToFile(Bitmap bmp, File fileToWrite){
        try {
            System.out.println("l'image est save en" + fileToWrite.getName());
            FileOutputStream stream = new FileOutputStream(fileToWrite);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            //Cleanup
            stream.close();
            bmp.recycle();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File createImageFile() {
        // Create an image file name
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = appCompatActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".png",         /* suffix */
                    storageDir      /* directory */
            );
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
