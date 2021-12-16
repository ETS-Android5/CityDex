package com.tlbail.ptuts3androidapp.Model.ObjectDetection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;

import com.tlbail.ptuts3androidapp.Model.OCR.OcrErrorException;

import org.tensorflow.lite.support.image.TensorImage;

import java.io.IOException;
import java.util.List;

import com.tlbail.ptuts3androidapp.ml.Model;

public class ObjectDetector {


    private Context context;

    private Model.DetectionResult result;

    public ObjectDetector(Context context) {
        this.context = context;
    }


    private RectF rectLocation;

    public void runObjectDetection(Bitmap bitmap){
        try {
            Model model = Model.newInstance(context);


            TensorImage image = TensorImage.fromBitmap(bitmap);

            Model.Outputs outputs = model.process(image);

            List<Model.DetectionResult> detectionResults = outputs.getDetectionResultList();
            Model.DetectionResult bestResult = outputs.getDetectionResultList().get(0);

            // Gets result from DetectionResult.
            float score = bestResult.getScoreAsFloat();
            rectLocation = bestResult.getLocationAsRectF();
            String objectFind = bestResult.getCategoryAsString();

            System.out.println("objectFind = " + objectFind);
            System.out.println("score = " + bestResult.getScoreAsFloat());
            System.out.println("category.left = " + rectLocation.left);
            System.out.println("category.width() = " + rectLocation.width());
            System.out.println("category.top = " + rectLocation.top);
            System.out.println("category.height() = " + rectLocation.height());

            // Releases model resources if no longer used.
            model.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public RectF getRect() throws OcrErrorException {
        return rectLocation;
    }

}
