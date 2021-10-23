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
        UserPropertyFileLoader userPropertyLoader = new UserPropertyFileLoader(context);
        Log.i("testAccessToDirectory", "userPropertyLoader.getDirectory() = " + userPropertyLoader.getDirectory());
    }


    @Test
    public void getUserProperty() {



    }
}