package com.tlbail.ptuts3androidapp.Model.Photo;

import android.net.Uri;

import java.io.Serializable;

public class Photo implements Serializable {

    private String photoUri;
    private String location;

    public Photo(Uri photoUri, String location) {
        this.photoUri = photoUri.toString();
        this.location = location;
    }

    public String getLocation() {
        return location;
    }


    public Uri getPhotoUri() {
        return Uri.parse(photoUri);
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photoUri='" + photoUri + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
