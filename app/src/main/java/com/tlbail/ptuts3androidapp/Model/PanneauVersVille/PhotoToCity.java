package com.tlbail.ptuts3androidapp.Model.PanneauVersVille;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.tlbail.ptuts3androidapp.Controller.PhotoActivity;
import com.tlbail.ptuts3androidapp.Controller.ResultActivity;
import com.tlbail.ptuts3androidapp.Model.City.City;
import com.tlbail.ptuts3androidapp.Model.City.CityData;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchByName;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCityListener;
import com.tlbail.ptuts3androidapp.Model.Localisation.LocationListener;
import com.tlbail.ptuts3androidapp.Model.Localisation.LocationManager;
import com.tlbail.ptuts3androidapp.Model.OCR.OcrResultListener;
import com.tlbail.ptuts3androidapp.Model.ObjectDetection.NoSignInImageException;
import com.tlbail.ptuts3androidapp.Model.Utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class PhotoToCity implements FetchCityListener,OcrResultListener, LocationListener {

    private CityDetectorInPhoto cityDetectorInPhoto;
    private LocationManager locationManager;
    private UserCityManager userCityManager;

    private AppCompatActivity appCompatActivity;

    private String ocrResult;
    private String locationResult;
    public List<CityFoundListener> cityFoundListeners = new ArrayList<>();

    private boolean verifLocationIsActivated = false;//TODO enlever lorsque c'est une version de production

    public PhotoToCity(ResultActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
        cityDetectorInPhoto = new CityDetectorInPhoto(appCompatActivity, this);
        userCityManager = new UserCityManager(appCompatActivity);
        locationManager = new LocationManager(appCompatActivity, this);
    }

    public void start(Bitmap photoToTransform, Uri photoUri){
        userCityManager.setPhotoUri(photoUri);
        try {
            cityDetectorInPhoto.start(photoToTransform);
        } catch (NoSignInImageException e) {
            e.printStackTrace();
            fail("Pas de panneau dans la photo. Reprenez une photo");
        }
    }

    @Override
    public void onOcrFinish(String result) {
        ocrResult = result;
        locationManager.getLocation();
    }

    @Override
    public void onLocationReceived(String location) {
        locationResult = location;
        transformToCity();
    }

    @Override
    public void onLocationFailed(String message) {
        fail(message);
    }

    private void transformToCity(){
        if(!verifLocationIsActivated)
            getCityDataByName(ocrResult);
        else if(isLocationAndOcrMatching())
            getCityDataByName(locationResult);
        else
            fail("Localisation différente du nom de ville");
    }

    private void getCityDataByName(String cityName) {
        FetchCity fetchCity = new FetchByName(this,cityName);
        fetchCity.execute();
    }

    private boolean isLocationAndOcrMatching() {
        return ocrResult.equalsIgnoreCase(locationResult) || StringUtils.similarity(ocrResult, locationResult) > 0.7;
    }

    @Override
    public void onDataQueryComplete(List<CityData> cityDataList) {
        if(cityDataList == null ||cityDataList.isEmpty()){
            fail("Aucune ville avec " + ocrResult +  " trouvée 😭");
            return;
        }
        CityData cityDataToReturn = getBestExistingCity(cityDataList);
        finish(cityDataToReturn);
    }

    private CityData getBestExistingCity(List<CityData> cityDataList){
        CityData cityDataToReturn = null;
        double threshold = 0.7;
        for(CityData cityData : cityDataList){
            double similarity = StringUtils.similarity(cityData.getName(), ocrResult);
            if(cityData.getName().equalsIgnoreCase(locationResult)) {
                cityDataToReturn = cityData;
                break;
            }
            else if(similarity >= threshold){
                cityDataToReturn = cityData;
                if(cityData.getName().equalsIgnoreCase(ocrResult))
                    cityDataToReturn = cityData;
            }
            threshold = similarity;
        }
        if(cityDataToReturn == null) cityDataToReturn = cityDataList.get(0);
        return cityDataToReturn;
    }

    private void finish(CityData cityData){
        if(userCityManager.isCityAlreadyOwned(cityData))
            fail(" Ville déjà obtenue ");
        else
            addCityToUser(userCityManager.createCity(cityData));
    }

    public void addCityToUser(City city) {
        userCityManager.addCityToOwnCity(city);
        findCity(city);
    }

    private void findCity(City city){
        for (CityFoundListener listener : cityFoundListeners) {
            listener.onCityFound(city);
        }
    }

    private void couldNotFindCity(){
        for (CityFoundListener listener : cityFoundListeners) {
            listener.onCityNotFound();
        }
    }

    private void fail(String message){
        makeToast(message);
        userCityManager.deletePhotoCityFromLocalStorage();
        couldNotFindCity();
    }

    private void makeToast(String message){
        appCompatActivity.runOnUiThread(() -> Toast.makeText(appCompatActivity, message, Toast.LENGTH_LONG).show());
    }

    public void subscribeOnCityFound(CityFoundListener cityFoundListener){
        cityFoundListeners.add(cityFoundListener);
    }

    private void startPhotoIntent(){
        Intent intent = new Intent(appCompatActivity, PhotoActivity.class);
        appCompatActivity.startActivity(intent);
    }
}
