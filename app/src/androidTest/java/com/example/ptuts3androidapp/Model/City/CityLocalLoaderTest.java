package com.example.ptuts3androidapp.Model.City;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.example.ptuts3androidapp.Model.Photo.Photo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CityLocalLoaderTest {


    private Context context;
    private CityLocalLoader cityLocalLoader;

    @Before
    public void setUp(){
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Assert.assertNotNull(context);
        cityLocalLoader = new CityLocalLoader(context);
        cityLocalLoader.setCities(generateCities());
    }


    @Test
    public void serialiseCity(){

        String DATA_FILE_NAME = "testCity.xml";

        City city = generateCity();
        City resultCity = null;

        try {
            FileOutputStream fOut = context.openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oosOfUserProperty = new ObjectOutputStream(fOut);
            oosOfUserProperty.writeObject(city);
            fOut.close();
            oosOfUserProperty.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fileInputStream = context.openFileInput(DATA_FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            resultCity = (City) ois.readObject();
            fileInputStream.close();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(resultCity);
        Assert.assertEquals(city.toString(), resultCity.toString());


    }

    @Test
    public void serialiseListOfCity(){

        String DATA_FILE_NAME = "testListCity.xml";

        List<City> cities = generateCities();
        List<City> resultCity = null;

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

        try {
            FileInputStream fileInputStream = context.openFileInput(DATA_FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            resultCity = (List<City>) ois.readObject();
            fileInputStream.close();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(resultCity);
        Assert.assertEquals(cities.toString(), resultCity.toString());


    }



    @Test
    public void userPropertyGetedIsnotNull(){
        Assert.assertNotNull(cityLocalLoader.getCities());
        Log.i("test", cityLocalLoader.getCities().toString());
    }

    @Test
    public void userPropertyDifferentWhenSet(){

        List<City> oldcities = cityLocalLoader.getCities();
        cityLocalLoader.setCities(generateCities());
        Assert.assertNotEquals(cityLocalLoader.getCities(),oldcities);
    }


    private List<City> generateCities(){

        List<City> cities = new ArrayList<>();
        cities.add(generateCity());
        return cities;
    }


    private City generateCity(){
        HashMap<String, String> properties = new HashMap();
        properties.put("test", String.valueOf(Math.random() * 2000));
        return new City(new Photo(Uri.parse(""), ""), new CityData(properties));
    }
}
