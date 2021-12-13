package com.tlbail.ptuts3androidapp.Model.ObjectDetection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.tlbail.ptuts3androidapp.Model.OCR.OcrErrorException;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.vision.detector.Detection;

import java.io.IOException;
import java.sql.Ref;
import java.util.List;

import com.tlbail.ptuts3androidapp.ml.Model;
import com.tlbail.ptuts3androidapp.ml.SsdMobilenetV11Metadata1;

public class ObjectDetector {


    private Context context;

    private Model.DetectionResult result;

    public ObjectDetector(Context context) {
        this.context = context;
    }


    private RectF category;

    public void runObjectDetection(Bitmap bitmap){
        try {
            Model model = Model.newInstance(context);


            bitmap = Bitmap.createScaledBitmap(bitmap, 300,300, true);
            TensorImage image = TensorImage.fromBitmap(bitmap);

            Model.Outputs outputs = model.process(image);
            Model.DetectionResult detectionResult = outputs.getDetectionResultList().get(0);

            // Gets result from DetectionResult.
            float location = detectionResult.getScoreAsFloat();
            category = detectionResult.getLocationAsRectF();
            String score = detectionResult.getCategoryAsString();

            System.out.println("score = " + score);
            
            // Releases model resources if no longer used.
            model.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public RectF getRect() throws OcrErrorException {
        return category;
    }

}
