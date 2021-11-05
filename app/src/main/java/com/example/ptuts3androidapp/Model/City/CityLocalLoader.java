package com.example.ptuts3androidapp.Model.City;

import android.content.Context;

import com.example.ptuts3androidapp.Model.Photo.Photo;
import com.example.ptuts3androidapp.Model.User.UserProperty;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CityLocalLoader implements Serializable{

    private static final String DATA_FILE_NAME = "citydata.xml";
    private Context context;
    File photoDirectory;

    public CityLocalLoader(Context context){
        this.context = context;
        photoDirectory = context.getFilesDir();
        if(getCities() == null){
            setLocalCities(new ArrayList<City>());
        }
    }



    public List<City> getCities() {
        try {
            FileInputStream fileInputStream = context.openFileInput(DATA_FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            List<City> cities = (List<City>) ois.readObject();
            fileInputStream.close();
            ois.close();
            return cities;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setLocalCities(List<City> cities) {
        try {
            FileOutputStream fOut = context.openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oosOfUserProperty = new ObjectOutputStream(fOut);
            oosOfUserProperty.writeObject(cities);
            fOut.close();
            oosOfUserProperty.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
