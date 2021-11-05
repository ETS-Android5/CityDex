package com.example.ptuts3androidapp.Model.User.LocalDataLoader;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.ptuts3androidapp.Model.User.UserProperty;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.LinkedHashMap;

@RunWith(AndroidJUnit4.class)
public class UserPropertyFileLoaderTest  {

    private Context context;
    private UserPropertyLocalLoader userPropertyFileLoader;

    @Before
    public void setUp(){
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Assert.assertNotNull(context);
        userPropertyFileLoader = new UserPropertyLocalLoader(context);
        userPropertyFileLoader.setUserProperty(generateRandomUserProperties());
    }



    @Test
    public void userPropertyGetedIsnotNull(){
        Assert.assertNotNull(userPropertyFileLoader.getUserProperty());
        Log.i("test",userPropertyFileLoader.getUserProperty().toString());
    }

    @Test
    public void userPropertyDifferentWhenSet(){
        UserProperty userProperty = generateRandomUserProperties();
        UserProperty previousproperty = userPropertyFileLoader.getUserProperty();
        userPropertyFileLoader.setUserProperty(userProperty);
        Assert.assertNotEquals(userPropertyFileLoader.getUserProperty(),previousproperty);
    }

    @Test
    public void userPropertyCoresspondToPreviousSet(){
        UserProperty userProperty = generateRandomUserProperties();
        userPropertyFileLoader.setUserProperty(userProperty);
        Assert.assertEquals(userPropertyFileLoader.getUserProperty(), userProperty);

    }


    private UserProperty generateRandomUserProperties(){

        HashMap<String, String> userData = new LinkedHashMap<>();
        userData.put("test", Double.toString(Math.random() * 1000));
        return new UserProperty(userData);
    }

}