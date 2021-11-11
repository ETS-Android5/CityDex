package com.example.ptuts3androidapp.Model.DetectionTextPanneau;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ptuts3androidapp.Model.OCR.OCRDetection;
import com.example.ptuts3androidapp.Model.OCR.ObjectDetector;
import com.example.ptuts3androidapp.Model.OCR.OcrErrorException;
import com.example.ptuts3androidapp.Model.OCR.OcrResultListener;

import java.io.IOException;

public class OCRFromObjectDetector  implements OcrResultListener {

    //Attributs
    private com.example.ptuts3androidapp.Model.OCR.OCRDetection OCRDetection;
    private ObjectDetector objectDetector;
    private Context context;
    private TextView textView;

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
    public void runObjetDetectionAndOCR(Bitmap bitmap, TextView view, ImageView img){
        this.textView = view;
        //Object detection
        objectDetector.runObjectDetection(bitmap);
        objectDetector.displayData(view);
        bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
        //OCR detection
        try {
            bitmap = OCRDetection.cropImage(bitmap, objectDetector.getRect());
            bitmap = OCRDetection.toGrayscale(bitmap);
            OCRDetection.runOcrResult(this, bitmap);
            img.setImageBitmap(bitmap);
        } catch (OcrErrorException e) {
            e.printStackTrace();
            abortOcrDetection(view);
        }
    }

    private void abortOcrDetection(TextView textView) {
        textView.setText("erreur aucun objet detecté !!!");
    }

    @Override
    public void onOcrFinish(String result) {
        textView.setText(textView.getText() + result);
    }
}
