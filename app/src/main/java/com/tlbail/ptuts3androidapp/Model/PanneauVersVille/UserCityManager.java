package com.tlbail.ptuts3androidapp.Model.PanneauVersVille;

import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import com.tlbail.ptuts3androidapp.Model.City.City;
import com.tlbail.ptuts3androidapp.Model.City.CityData;
import com.tlbail.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.tlbail.ptuts3androidapp.Model.Photo.Photo;
import com.tlbail.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.User;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class UserCityManager {

    private User user;
    public UserCityManager(AppCompatActivity activity){
         user = new User(new UserPropertyLocalLoader(activity), new CityLocalLoader(activity));
    }

    private Uri photoUri;

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public City createCity(CityData cityData){
        if(isDataIncorrect(cityData)) return null;
        return new City(new Photo(photoUri, cityData.getName()), cityData);
    }
    private boolean isDataIncorrect(CityData cityData) {
        return cityData == null;
    }

    public void deletePhotoCityFromLocalStorage() {
        try {
            File file = new File(new URI(photoUri.toString()));
            file.delete();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void addCityToOwnCity(City city) {
        List<City> cities = user.getOwnedCity();
        cities.add(city);
        user.setOwnedCity(cities);
    }

    public boolean isCityAlreadyOwned(CityData cityData) {
        return user.isCityAlreadyOwned(cityData);
    }
}
