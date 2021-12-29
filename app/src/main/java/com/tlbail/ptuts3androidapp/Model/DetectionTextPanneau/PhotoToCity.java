package com.tlbail.ptuts3androidapp.Model.DetectionTextPanneau;

import android.graphics.Bitmap;
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.tlbail.ptuts3androidapp.Controller.ReglageActivity;
import com.tlbail.ptuts3androidapp.CropUtils;
import com.tlbail.ptuts3androidapp.Model.City.City;
import com.tlbail.ptuts3androidapp.Model.City.CityData;
import com.tlbail.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity.FetchCityListener;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity.FetchByName;
import com.tlbail.ptuts3androidapp.Model.CityApi.FetchCity.FetchCity;
import com.tlbail.ptuts3androidapp.Model.Localisation.LocalisationManager;
import com.tlbail.ptuts3androidapp.Model.OCR.OCRDetection;
import com.tlbail.ptuts3androidapp.Model.OCR.OcrErrorException;
import com.tlbail.ptuts3androidapp.Model.ObjectDetection.ObjectDetector;
import com.tlbail.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.User;
import com.tlbail.ptuts3androidapp.R;

import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class PhotoToCity implements FetchCityListener {


    private static final long DEFAULTTIMEOUT = 10;
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
    private boolean verifLocatIsActivated = true;
    private CropUtils mCropUtils;

    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }


    public PhotoToCity(AppCompatActivity appCompatActivity){
        this.appCompatActivity = appCompatActivity;
        mCropUtils = new CropUtils();
        objectDetector = new ObjectDetector(appCompatActivity);
        try {
            ocrDetection = new OCRDetection(appCompatActivity.getAssets().open("fra.traineddata"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = new User(new UserPropertyLocalLoader(appCompatActivity), new CityLocalLoader(appCompatActivity));
        if(user.containsKey(ReglageActivity.VERIFLOCATKEY)) {
            if(user.get(ReglageActivity.VERIFLOCATKEY).equals(String.valueOf(false))){
                verifLocatIsActivated = false;
            }else {
                verifLocatIsActivated = true;
            }
        }


    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {this.bitmap = bitmap;}

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void start(Bitmap bitmap){
        this.bitmap =bitmap;
        startObjectDetection();
        try {
            if(objectDetector.getRect() == null){
                fail();
                return;
            }
        } catch (OcrErrorException e) {
            e.printStackTrace();
        }
        startOCR();
        if(verifLocatIsActivated){
            startLocalisation();
            startTimeOut();
        }
    }


    private void startObjectDetection() {
        objectDetector.runObjectDetection(bitmap);
    }


    private void startOCR() {
        //OCR detection
        try {
            ocrDetection.runOcrResult(this, objectDetector.getRect());
        } catch (OcrErrorException e) {
            e.printStackTrace();
            appCompatActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(appCompatActivity,"Aucun texte trouvé !", Toast.LENGTH_LONG);
                }
            });
            fail();

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startLocalisation() {
        localisationManager = new LocalisationManager(this);
        localisationManager.start();
    }

    private void startTimeOut() {
        User user = new User(new UserPropertyLocalLoader(getAppCompatActivity()), new CityLocalLoader(getAppCompatActivity()));

        final long timeLocationTimeOut;
        if(user.containsKey(ReglageActivity.LOCATIONTIMEOUTKEY)){
            timeLocationTimeOut = Long.parseLong(user.get(ReglageActivity.LOCATIONTIMEOUTKEY));
            if(timeLocationTimeOut == -1){
                return;
            }
        }else{
            timeLocationTimeOut = DEFAULTTIMEOUT;
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(timeLocationTimeOut * 1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                if(!ocrHaveCompleted || !locationhaveCompleted ){
                    appCompatActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(appCompatActivity, "Localisation indisponible", Toast.LENGTH_SHORT).show();
                        }
                    });
                    fail();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }


    public void setOcrResult(String result){
        if(result.isEmpty()) {
            appCompatActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(appCompatActivity, "Aucun texte trouvé ", Toast.LENGTH_SHORT).show();
                }
            });
            fail();
            return;
        }
        ocrHaveCompleted = true;
        this.resultOcr = result;
        System.out.println("Result OCR après regex = " + resultOcr);
        finish();
    }

    public void setLocationResult(String result){
        locationhaveCompleted = true;
        locationResult = result;
        finish();
    }


    private void finish() {

        if(ocrHaveCompleted){
            TextView textView = appCompatActivity.findViewById(R.id.resutlTextview);
            appCompatActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(resultOcr);
                }
            });
        }
        if(ocrHaveCompleted  && locationhaveCompleted){
            System.out.println("OCR et localisation terminés");
            System.out.println("% de similtude : " + mCropUtils.similarity(resultOcr.toUpperCase(), locationResult.toUpperCase()));

            if(resultOcr.toUpperCase().contains(locationResult.toUpperCase()) || mCropUtils.similarity(resultOcr.toUpperCase(), locationResult.toUpperCase()) > 0.7){
                getCityDataByName(locationResult);
            }else{
                appCompatActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(appCompatActivity, "La localisation ne matche pas", Toast.LENGTH_LONG).show();
                    }
                });
                fail();
            }
        }

        if(ocrHaveCompleted && !verifLocatIsActivated){
            System.out.println("Ocr terminé et Localisation desactivé");
            getCityDataByName(resultOcr);
        }
    }


    private void getCityDataByName(String cityname) {
        FetchCity fetchCity = new FetchByName(this,cityname);
        fetchCity.execute();
    }

    @Override
    public void onDataQueryComplete(List<CityData> cityDatas) {
        if(cityDatas.size() <= 0)
        {
            appCompatActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(appCompatActivity, "Aucne ville avec " + resultOcr +  " trouvée 😭", Toast.LENGTH_SHORT).show();
                }
            });

            fail();
            return;
        }
        CityData cityDataToReturn = null;
        for(CityData cityData : cityDatas){
            if(cityData.getName().equalsIgnoreCase(locationResult))
                cityDataToReturn = cityData;
        }
        if(cityDataToReturn == null) cityDataToReturn = cityDatas.get(0);
        finishWithData(cityDataToReturn);
    }

    private void finishWithData(CityData cityData){
        if(cityIsAlreadyOwn(cityData)) {
            appCompatActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(appCompatActivity, " Ville déjà obtenue ", Toast.LENGTH_LONG).show();
                }
            });
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
        //Todo check si la location est bonne et si on a bien trouvé une ville
        if(cityData == null) return false;
        if(verifLocatIsActivated){
            return locationResult == null || locationResult.isEmpty() ||
                    resultOcr == null || resultOcr.isEmpty();
        }else{
            return resultOcr == null || resultOcr.isEmpty();
        }

    }

    private void fail(){
        updateListener(null);
    }

    public void onResume() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(localisationManager != null) localisationManager.onResumeActivity();
        }

    }

    public void onPause() {
        if(localisationManager != null) localisationManager.desabonnementGPS();
    }
}
