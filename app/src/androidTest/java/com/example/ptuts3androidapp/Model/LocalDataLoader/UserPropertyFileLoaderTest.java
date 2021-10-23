package com.example.ptuts3androidapp.Model.LocalDataLoader;

import static org.junit.Assert.*;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.ptuts3androidapp.Model.User;
import com.example.ptuts3androidapp.Model.UserProperty;
import com.example.ptuts3androidapp.Model.UserPropertyLoader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.LinkedHashMap;

@RunWith(AndroidJUnit4.class)
public class UserPropertyFileLoaderTest {

    private Context context;
    private UserPropertyFileLoader userPropertyFileLoader;

    @Before
    public void setUp(){
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Assert.assertNotNull(context);
        userPropertyFileLoader = new UserPropertyFileLoader(context);
        userPropertyFileLoader.setUserProperty(new UserProperty(new HashMap<>()));
    }

    @Test
    public void testAccessToDirectory(){
        Assert.assertNotNull(userPropertyFileLoader.getDirectory());
        Log.i("testAccessToDirectory", "userPropertyLoader.getDirectory() = " + userPropertyFileLoader.getDirectory());

    }


    @Test
    public void userPropertyGetedIsnotNull(){
        Assert.assertNotNull(userPropertyFileLoader.getUserProperty());
        Log.i("test",userPropertyFileLoader.getUserProperty().toString());
    }

    @Test
    public void userPropertyDifferentWhenSet(){
        HashMap<String, String> userData = new LinkedHashMap<>();
        UserProperty userProperty = new UserProperty(userData);
        UserProperty previousproperty = userPropertyFileLoader.getUserProperty();
        userPropertyFileLoader.setUserProperty(userProperty);
        Assert.assertEquals(userPropertyFileLoader.getUserProperty(), userProperty);
        //Assert.assertNotEquals(userPropertyFileLoader.getUserProperty(),previousproperty);


    }



}