package com.example.ptuts3androidapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.TextView;

import com.example.ptuts3androidapp.ml.SsdMobilenetV11Metadata1;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.vision.detector.Detection;

import java.io.IOException;
import java.util.List;

public class ObjectDetector {


    private Context context;

    private List<Detection> result;

    public ObjectDetector(Context context) {
        this.context = context;
    }

    public void runObjectDetection(Bitmap bitmap){
        try {
            SsdMobilenetV11Metadata1 model = SsdMobilenetV11Metadata1.newInstance(context);
        } catch (IOException e) {
            e.printStackTrace();
        }


        bitmap = Bitmap.createScaledBitmap(bitmap, 300,300, true);
        TensorImage image = TensorImage.fromBitmap(bitmap);


        try {
            org.tensorflow.lite.task.vision.detector.ObjectDetector.ObjectDetectorOptions objectDetectorOptions = org.tensorflow.lite.task.vision.detector.ObjectDetector.ObjectDetectorOptions.builder().setMaxResults(5).setScoreThreshold(0.5f).build();
            org.tensorflow.lite.task.vision.detector.ObjectDetector objectDetector = org.tensorflow.lite.task.vision.detector.ObjectDetector.createFromFileAndOptions(context, "mdel.tflite", objectDetectorOptions);
            result = objectDetector.detect(image);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public  void displayData(TextView textViewResult) {
        String resultText = "";
        for (Detection detection : result){
            resultText += "position de l'objet : (" + detection.getBoundingBox().left + " | " +
                    detection.getBoundingBox().top + ") | (" + detection.getBoundingBox().right + " | " +
                    detection.getBoundingBox().bottom + " ) ";
            resultText += "\nje pense que c'est : ";
            for (Category category: detection.getCategories()){
                resultText += " - " + category.getLabel();

            }
        }
        textViewResult.setText(resultText);
    }

}
