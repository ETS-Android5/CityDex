package com.example.ptuts3androidapp.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class User implements Map<String, String> {

    private String userName;
    private HashMap<String, String> properties;
    private UserPropertyLoader userPropertyLoader;

    public User(String userName, UserPropertyLoader userPropertyLoader) {
        this.userPropertyLoader = userPropertyLoader;
        this.userName = userName;
        this.properties = userPropertyLoader.getUserProperty().getProperties();
    }


    /**
     * the map returned is unmodifiable please use setProperty
     * @return
     */
    public Map<String, String> getUserProperties() {
        return Collections.unmodifiableMap(properties);
    }

    public void  setProperty(String key, String value){
        properties.put(key, value);
        updateUserProperty();
    }

    private void updateUserProperty(){
        userPropertyLoader.setUserProperty(new UserProperty(properties));
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public int size() {
        return properties.size();
    }

    @Override
    public boolean isEmpty() {
        return properties.isEmpty();
    }

    @Override
    public boolean containsKey(@Nullable Object key) {
        return properties.containsKey(key);
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        return properties.containsValue(value);
    }

    @Nullable
    @Override
    public String get(@Nullable Object key) {
        return properties.get(key);
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        String stringToReturn = properties.put(key, value);
        updateUserProperty();
        return stringToReturn;
    }

    @Nullable
    @Override
    public String remove(@Nullable Object key) {
        String stringToReturn = properties.remove(key);
        updateUserProperty();
        return stringToReturn;
    }

    @Override
    public void putAll(@NonNull Map<? extends String, ? extends String> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        properties.clear();
        updateUserProperty();
    }

    @NonNull
    @Override
    public Set<String> keySet() {
        return properties.keySet();
    }

    @NonNull
    @Override
    public Collection<String> values() {
        return properties.values();
    }

    @NonNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        return properties.entrySet();
    }
}
