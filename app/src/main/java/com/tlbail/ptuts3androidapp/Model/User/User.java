package com.tlbail.ptuts3androidapp.Model.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tlbail.ptuts3androidapp.Model.City.City;
import com.tlbail.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class User implements Map<String, String> {

    private HashMap<String, String> properties;
    private UserPropertyLoader userPropertyLoader;
    private List<City> ownedCity;
    private CityLocalLoader cityLocalLoader;

    public User(UserPropertyLoader userPropertyLoader, CityLocalLoader cityLocalLoader) {
        this.userPropertyLoader = userPropertyLoader;
        this.properties = userPropertyLoader.getUserProperty().getProperties();
        this.cityLocalLoader = cityLocalLoader;
        ownedCity = cityLocalLoader.getCities();

    }


    public List<City> getOwnedCity() {
        return ownedCity;
    }

    public void setOwnedCity(List<City> ownedCity) {
        this.ownedCity = ownedCity;
        cityLocalLoader.setCities(ownedCity);
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
