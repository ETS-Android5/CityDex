package com.example.ptuts3androidapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.IOException;

public class OCRFromObjectDetector {

    //Attributs
    private TessOCR tessOCR;
    private ObjectDetector objectDetector;
    private Context context;

    //Constructeur
    public OCRFromObjectDetector(Context context){

        //Creates objectDetector and TessOCR
        objectDetector = new ObjectDetector(context);

        try {
            tessOCR = new TessOCR(context.getAssets().open("fra.traineddata"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Getters
    public TessOCR getTessOCR() {
        return tessOCR;
    }

    public ObjectDetector getObjectDetector() {
        return objectDetector;
    }

    //Methode qui lance la détection d'objet + qui renvoie le résultat OCR
    public void getOCRandObjectResult(Bitmap bitmap, TextView view, ImageView img){

        //Object detection
        objectDetector.runObjectDetection(bitmap);
        objectDetector.displayData(view);

        bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);

        //OCR detection
        bitmap = tessOCR.cropImage(bitmap, objectDetector.getRect());
        bitmap = tessOCR.toGrayscale(bitmap);
        img.setImageBitmap(bitmap);
        //view.setText(view.getText() + tessOCR.getOCRResult(bitmap));
    }
}
