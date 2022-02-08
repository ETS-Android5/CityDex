package com.tlbail.ptuts3androidapp.Model.PanneauVersVille;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.tlbail.ptuts3androidapp.Controller.ReglageActivity;
import com.tlbail.ptuts3androidapp.Model.Localisation.LocalizationListener;
import com.tlbail.ptuts3androidapp.Model.OCR.OcrResultListener;
import com.tlbail.ptuts3androidapp.Model.Utils.StringUtils;
import com.tlbail.ptuts3androidapp.Model.City.City;
import com.tlbail.ptuts3androidapp.Model.City.CityData;
import com.tlbail.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCityListener;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchByName;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity;
import com.tlbail.ptuts3androidapp.Model.Localisation.LocalizationManager;
import com.tlbail.ptuts3androidapp.Model.OCR.CityNameOCRDetector;
import com.tlbail.ptuts3androidapp.Model.ObjectDetection.ObjectDetector;
import com.tlbail.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.User;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class PhotoToCity implements FetchCityListener, LocalizationListener, OcrResultListener {

    private CityNameOCRDetector ocrDetector;
    private LocalizationManager localizationManager;
    private ObjectDetector objectDetector;
    protected AppCompatActivity appCompatActivity;
    private Bitmap photoToTransform;
    private String ocrResult;
    private String locationResult;
    public List<CityFoundListener> cityFoundListeners = new ArrayList<>();


    private boolean verifLocationIsActivated = false;

    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }


    public PhotoToCity(AppCompatActivity appCompatActivity){
        this.appCompatActivity = appCompatActivity;
        objectDetector = ObjectDetector.getInstance(appCompatActivity);
        ocrDetector = new CityNameOCRDetector(this);
        localizationManager = new LocalizationManager(getAppCompatActivity(), this);

        User user = new User(new UserPropertyLocalLoader(appCompatActivity), new CityLocalLoader(appCompatActivity));
        if(user.containsKey(ReglageActivity.VERIFLOCATKEY))
            verifLocationIsActivated =user.get(ReglageActivity.VERIFLOCATKEY).equals(String.valueOf(false));
    }

    public Bitmap getPhotoToTransform() {
        return photoToTransform;
    }

    public void setPhotoToTransform(Bitmap photoToTransform) {this.photoToTransform = photoToTransform;}

    public void start(Bitmap photoToTransform){
        this.photoToTransform = photoToTransform;
        startObjectDetection();
        RectF signLocationInPhoto = objectDetector.getRect();
        ocrDetector.runOcrResult(signLocationInPhoto);
    }

    @Override
    public void onOcrFinish(String result) {
        ocrResult = result;
        localizationManager.getLocation();
    }

    @Override
    public void onLocationReceived(String location) {
        locationResult = location;
        transformToCity();
    }

    private void transformToCity(){
        if(!verifLocationIsActivated)
            getCityDataByName(ocrResult);
        else if(isLocationAndOcrMatching())
            getCityDataByName(locationResult);
        else
            makeToast("Localisation différente du nom de ville");
    }

    private boolean isLocationAndOcrMatching() {
        return ocrResult.equalsIgnoreCase(locationResult) || StringUtils.similarity(ocrResult, locationResult) > 0.7;
    }

    private void startObjectDetection() {
        objectDetector.runObjectDetection(photoToTransform);
    }

    private void makeToast(String message){
        appCompatActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(appCompatActivity, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getCityDataByName(String cityName) {
        FetchCity fetchCity = new FetchByName(this,cityName);
        fetchCity.execute();
    }

    @Override
    public void onDataQueryComplete(List<CityData> cityDataList) {
        if(cityDataList == null ||cityDataList.isEmpty()){
            makeToast("Aucne ville avec " + ocrResult +  " trouvée 😭");
            fail();
            return;
        }
        CityData cityDataToReturn = getBestExistingCity(cityDataList);
        finishWithData(cityDataToReturn);
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
            else if(similarity > threshold){
                cityDataToReturn = cityData;
            }
            threshold = similarity;
        }
        if(cityDataToReturn == null) cityDataToReturn = cityDataList.get(0);
        return cityDataToReturn;
    }


    private void finishWithData(CityData cityData){
        if(cityIsAlreadyOwn(cityData)) {
            makeToast(" Ville déjà obtenue ");
            fail();
            return;
        }
        updateListener(createCity(cityData));
    }

    private boolean cityIsAlreadyOwn(CityData cityData) {
        User user = new User(new UserPropertyLocalLoader(appCompatActivity),new CityLocalLoader(appCompatActivity) );
        Iterator<City> ownCity = user.getOwnedCity().iterator();
        while (ownCity.hasNext()){
            CityData cityData1 = ownCity.next().getCityData();
            if(cityData1.getName().equalsIgnoreCase(cityData.getName())){
                return true;
            }
        }
        return false;
    }

    protected abstract City createCity(CityData city);


    protected boolean dataIsUncorrect(CityData cityData) {
        if(cityData == null) return false;
        if(verifLocationIsActivated){
            return locationResult == null || locationResult.isEmpty() ||
                    ocrResult == null || ocrResult.isEmpty();
        }else{
            return ocrResult == null || ocrResult.isEmpty();
        }

    }

    private void fail(){
        updateListener(null);
    }

    public void subscribeOnCityFound(CityFoundListener cityFoundListener){
        cityFoundListeners.add(cityFoundListener);
    }

    public void updateListener(City city){
        for(CityFoundListener cityFoundListener: cityFoundListeners){
            cityFoundListener.onCityFound(city);
        }
    }

}



