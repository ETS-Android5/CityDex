package com.tlbail.ptuts3androidapp.Model.ObjectDetection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.widget.Toast;

import org.tensorflow.lite.support.image.TensorImage;

import java.io.IOException;

import com.tlbail.ptuts3androidapp.ml.Model;

public class ObjectDetector {


    private Context context;
    private RectF rectLocation;
    private static ObjectDetector objectDetector;

    private ObjectDetector(Context context) {//TODO passer en singleton
        this.context = context;
    }

    public static ObjectDetector getInstance(Context context){
        if (objectDetector != null) return objectDetector;
        objectDetector = new ObjectDetector(context);
        return objectDetector;
    }

    public void runObjectDetection(Bitmap image){
        try {
            Model model = Model.newInstance(context);
            TensorImage tensorImage = TensorImage.fromBitmap(image);
            Model.Outputs outputs = model.process(tensorImage);
            Model.DetectionResult bestLocation = outputs.getDetectionResultList().get(0);
            rectLocation = getLocationFromDetection(image, bestLocation);
            model.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private RectF getLocationFromDetection(Bitmap image, Model.DetectionResult bestResult) {
        float score = bestResult.getScoreAsFloat();
        if(score < 0.2) fail();
        if(!isInImage(bestResult.getLocationAsRectF(), image)) fail();
        return bestResult.getLocationAsRectF();
    }

    public boolean isInImage(RectF rectLocation, Bitmap image){
        return  rectLocation.left > 0
                && rectLocation.top > 0
                && rectLocation.width() > 0
                && rectLocation.height() > 0
                && rectLocation.left + rectLocation.width() <= image.getWidth()
                && rectLocation.top + rectLocation.height() <= image.getHeight();
    }

    private void fail(){
        Toast.makeText(context.getApplicationContext(), "Pas de panneau", Toast.LENGTH_LONG).show();
        rectLocation = null;
    }

    public RectF getRect(){
        return rectLocation;
    }

}
