package com.tlbail.ptuts3androidapp.Model.ObjectDetection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.widget.Toast;

import org.tensorflow.lite.support.image.TensorImage;

import java.io.IOException;

import com.tlbail.ptuts3androidapp.ml.Model;

public class ObjectDetector {


    private RectF rectLocation;
    private static ObjectDetector objectDetector;
    private Model model;

    private ObjectDetector(Context context) {
        try {
            model = Model.newInstance(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObjectDetector getInstance(Context context){
        if (objectDetector != null) return objectDetector;
        objectDetector = new ObjectDetector(context);
        return objectDetector;
    }

    public RectF runObjectDetection(Bitmap image) throws NoSignInImageException{
        TensorImage tensorImage = TensorImage.fromBitmap(image);
        Model.Outputs outputs = model.process(tensorImage);
        Model.DetectionResult bestLocation = outputs.getDetectionResultList().get(0);
        RectF location = getLocationFromDetection(image, bestLocation);
        if(location == null) throw new NoSignInImageException();
        return location;
    }

    private RectF getLocationFromDetection(Bitmap image, Model.DetectionResult bestResult) {
        if(!isInImage(bestResult.getLocationAsRectF(), image)) return null;
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

    private void fail(){//TODO mettre le toast autre part
        //Toast.makeText(context.getApplicationContext(), "Pas de panneau", Toast.LENGTH_LONG).show();
        rectLocation = null;
    }
}
