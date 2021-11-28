package com.tlbail.ptuts3androidapp.Model.DetectionTextPanneau;

import android.graphics.Bitmap;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

import com.tlbail.ptuts3androidapp.Model.City.City;
import com.tlbail.ptuts3androidapp.Model.City.CityData;
import com.tlbail.ptuts3androidapp.Model.CityApi.Department;
import com.tlbail.ptuts3androidapp.Model.CityApi.Region;
import com.tlbail.ptuts3androidapp.Model.Localisation.LocalisationManager;
import com.tlbail.ptuts3androidapp.Model.OCR.OCRDetection;
import com.tlbail.ptuts3androidapp.Model.OCR.OcrErrorException;
import com.tlbail.ptuts3androidapp.Model.ObjectDetection.ObjectDetector;
import com.tlbail.ptuts3androidapp.Model.Photo.Photo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class PhotoToCity {


    public List<CityFoundListener> cityFoundListeners = new ArrayList<>();

    public void subscribeOnCityFound(CityFoundListener cityFoundListener){
        cityFoundListeners.add(cityFoundListener);
    }

    public void unsubscribeOnCityFound(CityFoundListener cityFoundListener){
        cityFoundListeners.remove(cityFoundListener);
    }

    public void updateListener(City city){
       for(CityFoundListener cityFoundListener: cityFoundListeners){
           cityFoundListener.onCityFoundt(city);
       }
    }




    private AppCompatActivity appCompatActivity;
    private LocalisationManager localisationManager;
    private ObjectDetector objectDetector;
    private OCRDetection ocrDetection;
    private boolean ocrHaveCompleted;
    private String resultOcr;
    private boolean locationhaveCompleted;
    private String locationResult;
    private Bitmap bitmap;
    private boolean objectDetectionCompleted;
    private String objetDetectionResult;

    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }


    public PhotoToCity(AppCompatActivity appCompatActivity){
        this.appCompatActivity = appCompatActivity;
        objectDetector = new ObjectDetector(appCompatActivity);
        try {
            ocrDetection = new OCRDetection(appCompatActivity.getAssets().open("fra.traineddata"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void start(Bitmap bitmap){
        this.bitmap =bitmap;
        startObjectDetection();
        startOCR();
        startLocalisation();
    }

    private void startObjectDetection() {
        objectDetector.runObjectDetection(bitmap);
        objetDetectionResult = objectDetector.getResultDataInText();
    }


    private void startOCR() {
        //ocrFromObjectDetector.runObjetDetectionAndOCR(bitmap);
        //OCR detection
        try {
            ocrDetection.runOcrResult(this, bitmap, objectDetector.getRect());
            //img.setImageBitmap(bitmap);
        } catch (OcrErrorException e) {
            e.printStackTrace();
        }
    }

    private void startLocalisation() {
        localisationManager = new LocalisationManager(this);
        localisationManager.requestPermission();
    }


    public void setOcrResult(String result){
        if(result.isEmpty()) {
            fail();
            return;
        }
        ocrHaveCompleted = true;
        this.resultOcr = result;
        finish();
    }

    public void setLocationResult(String result){
        locationhaveCompleted = true;
        locationResult = result;
        finish();
    }


    private void finish() {
        if(ocrHaveCompleted && locationhaveCompleted){
            updateListener(createCity(locationResult));
        }
    }

    protected abstract City createCity(String city);


    protected boolean dataIsUncorrect() {
        //Todo check si la location est bonne et si on a bien trouvé une ville
        return locationResult == null || locationResult.isEmpty() ||
                resultOcr == null || resultOcr.isEmpty();
    }

    private void fail(){
        updateListener(null);
    }

    public void onResume() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            localisationManager.onResumeActivity();
        }

    }

    public void onPause() {
        localisationManager.desabonnementGPS();
    }
}
