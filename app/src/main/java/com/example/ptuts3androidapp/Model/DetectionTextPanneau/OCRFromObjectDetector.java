package com.example.ptuts3androidapp.Model.DetectionTextPanneau;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.TextView;

import com.example.ptuts3androidapp.Model.OCR.OCRDetection;
import com.example.ptuts3androidapp.Model.ObjectDetection.ObjectDetector;
import com.example.ptuts3androidapp.Model.OCR.OcrErrorException;
import com.example.ptuts3androidapp.Model.OCR.OcrResultListener;

import java.io.IOException;

public class OCRFromObjectDetector  implements OcrResultListener {

    //Attributs
    private com.example.ptuts3androidapp.Model.OCR.OCRDetection OCRDetection;
    private ObjectDetector objectDetector;
    private OnPanneauResultFinishListener onPanneauResultFinishListener;
    private String objetDetectionResult;

    //Constructeur
    public OCRFromObjectDetector(Context context){

        //Creates objectDetector and TessOCR
        objectDetector = new ObjectDetector(context);

        try {
            OCRDetection = new OCRDetection(context.getAssets().open("fra.traineddata"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Methode qui lance la détection d'objet + qui renvoie le résultat OCR
    public void runObjetDetectionAndOCR(Bitmap bitmap, OnPanneauResultFinishListener onPanneauResultFinishListener){

        this.onPanneauResultFinishListener = onPanneauResultFinishListener;
        //Object detection
        objectDetector.runObjectDetection(bitmap);
        objetDetectionResult = objectDetector.getResultDataInText();
        bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
        //OCR detection
        try {
            bitmap = OCRDetection.cropImage(bitmap, objectDetector.getRect());
            bitmap = OCRDetection.toGrayscale(bitmap);
            OCRDetection.runOcrResult(this, bitmap);
            //img.setImageBitmap(bitmap);
        } catch (OcrErrorException e) {
            e.printStackTrace();
            abortOcrDetection();
        }
    }

    private void abortOcrDetection() {

        onPanneauResultFinishListener.onPanneauResultFinishListener(new ResultScan("erreur aucun text detecter", ""));
    }

    @Override
    public void onOcrFinish(String result) {
        onPanneauResultFinishListener.onPanneauResultFinishListener(new ResultScan(objetDetectionResult, result));
    }
}
