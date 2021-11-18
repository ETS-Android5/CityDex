package com.tlbail.ptuts3androidapp.Model.User;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class UserProperty implements Serializable {

    private HashMap<String, String> properties;


    public UserProperty(HashMap<String, String> properties) {
        this.properties = properties;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return "UserProperty{" +
                "properties=" + properties +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProperty that = (UserProperty) o;
        return Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties);
    }
}
