package com.example.ptuts3androidapp.Model.Photo.PhotoLocalLoader;

import android.content.Context;

import com.example.ptuts3androidapp.Model.Photo.PhotoLoader;

import java.io.File;
import java.util.List;

public class PhotoLocalLoader implements PhotoLoader {


    private Context context;
    private static final String PHOTO_DIRECTORY_NAME = "photo";
    File photoDirectory;

    public PhotoLocalLoader(Context context){
        this.context = context;
        photoDirectory = context.getFilesDir();
    }

    @Override
    public List<File> getPhotoFile() {
        return null;
    }
}
