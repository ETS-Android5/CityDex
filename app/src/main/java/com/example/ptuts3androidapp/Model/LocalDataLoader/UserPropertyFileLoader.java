package com.example.ptuts3androidapp.Model.LocalDataLoader;

import static android.os.ParcelFileDescriptor.MODE_WORLD_READABLE;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.ptuts3androidapp.Model.User;
import com.example.ptuts3androidapp.Model.UserProperty;
import com.example.ptuts3androidapp.Model.UserPropertyLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class UserPropertyFileLoader implements UserPropertyLoader {

    private static final String DATA_FILE_NAME = "data.xml";
    File directory;
    private Context context;


    public UserPropertyFileLoader(Context context){
        this.context = context;
        directory = context.getFilesDir();
    }

    public File getDirectory() {
        return directory;
    }


    @Override
    public UserProperty getUserProperty() {
        HashMap<String, String> userData = new LinkedHashMap<>();
        UserProperty userPropertyLoaded = new UserProperty(userData);
        File[] filesOfDirectory = directory.listFiles();
        for (File file: filesOfDirectory ) {
            Log.i("files23424" , file.toString());
        }
        try {
            FileInputStream fileInputStream = context.openFileInput(DATA_FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            UserProperty userProperty = (UserProperty) ois.readObject();
            fileInputStream.close();
            ois.close();
            return userProperty;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setUserProperty(UserProperty userProperty) {
        try {
            FileOutputStream fOut = context.openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oosOfUserProperty = new ObjectOutputStream(fOut);
            oosOfUserProperty.writeObject(userProperty);
            fOut.close();
            oosOfUserProperty.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
