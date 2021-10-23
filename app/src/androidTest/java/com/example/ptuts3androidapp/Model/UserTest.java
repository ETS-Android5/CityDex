package com.example.ptuts3androidapp.Model;

import static org.junit.Assert.*;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.ptuts3androidapp.Model.LocalDataLoader.UserPropertyFileLoader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class UserTest {

    private User user;
    private Context context;

    @Before
    public void setUp(){
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Assert.assertNotNull(context);
        user = generateRandomUser();
    }

    private User generateRandomUser(){
        String username = "bob " + Double.toString(Math.random() * 100);
        UserPropertyLoader userPropertyLoader = new UserPropertyFileLoader(context);
        User user = new User(username, userPropertyLoader);
        user.setProperty("test", "true");
        return user;

    }


    @Test
    public void newlygeneratedUserIsNotNull(){
        Assert.assertNotNull(user);
    }

    @Test
    public void propertiesOfUserIsNotNull(){
        Assert.assertNotNull(user.getUserProperties());
        Assert.assertNotNull(user.getUserName());
        Assert.assertNotNull(user.getUserProperties().get("test"));
    }

    @Test
    public void propertiesOfUserIsCorrect(){
        Assert.assertEquals("true" ,user.getUserProperties().get("test"));
    }

    @Test
    public void propertiesOfUserIsCorrectAfterChange(){
        user.setProperty("test", "false");
        Assert.assertEquals("false" ,user.getUserProperties().get("test"));
        user.setProperty("test", "true");
        Assert.assertEquals("true" ,user.getUserProperties().get("test"));

    }




}