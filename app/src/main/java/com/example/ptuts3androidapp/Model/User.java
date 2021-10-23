package com.example.ptuts3androidapp.Model;

import java.util.HashMap;

public class User {

    private String userName;
    private UserProperty userProperty;


    public User(String userName, UserPropertyLoader userPropertyLoader) {
        this.userProperty = userPropertyLoader.getUserProperty();
        this.userName = userName;
    }


    public UserProperty getUserProperty() {
        return userProperty;
    }


}
