package com.tlbail.ptuts3androidapp.Model.PanneauVersVille;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;

import com.tlbail.ptuts3androidapp.Model.OCR.CityNameOCRDetector;
import com.tlbail.ptuts3androidapp.Model.OCR.OcrResultListener;
import com.tlbail.ptuts3androidapp.Model.ObjectDetection.ObjectDetector;

public class CityDetectorInPhoto {

    private CityNameOCRDetector ocrDetector;
    private ObjectDetector objectDetector;

    public CityDetectorInPhoto(Context context, OcrResultListener ocrResultListener){
        objectDetector = ObjectDetector.getInstance(context);
        ocrDetector = new CityNameOCRDetector(ocrResultListener);
    }

    public void start(Bitmap imageToProcess){//TODO Faire en sorte que la methode retourne quelque chose
        RectF signLocationInImage = objectDetector.runObjectDetection(imageToProcess);
        if(signLocationInImage == null)
            return;
        ocrDetector.runOcrResult(imageToProcess, signLocationInImage);
    }
}
