package com.example.ptuts3androidapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.googlecode.tesseract.android.TessBaseAPI;

import org.w3c.dom.Text;

import java.io.IOException;

public class OCRFromObjectDetector  implements OcrResultListener{

    //Attributs
    private TessOCR tessOCR;
    private ObjectDetector objectDetector;
    private Context context;
    private TextView textView;

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

    //Methode qui lance la détection d'objet + qui renvoie le résultat OCR
    public void runObjetDetectionAndOCR(Bitmap bitmap, TextView view, ImageView img){
        this.textView = view;
        //Object detection
        objectDetector.runObjectDetection(bitmap);
        objectDetector.displayData(view);
        bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
        //OCR detection
        try {
            bitmap = tessOCR.cropImage(bitmap, objectDetector.getRect());
            bitmap = tessOCR.toGrayscale(bitmap);
            tessOCR.runOcrResult(this, bitmap);
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
