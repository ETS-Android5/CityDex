package com.tlbail.ptuts3androidapp.Model.ObjectDetection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.widget.Toast;

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
            if(score < 0.2) fail();

            rectLocation = bestResult.getLocationAsRectF();
            String objectFind = bestResult.getCategoryAsString();


            System.out.println("objectFind = " + objectFind);
            System.out.println("score = " + bestResult.getScoreAsFloat());
            System.out.println("category.left = " + rectLocation.left);
            System.out.println("category.width() = " + rectLocation.width());
            System.out.println("category.top = " + rectLocation.top);
            System.out.println("category.height() = " + rectLocation.height());

            if(     bestResult.getLocationAsRectF().left <= 0 ||
                    bestResult.getLocationAsRectF().top <= 0 ||
                    bestResult.getLocationAsRectF().width() <= 0 ||
                    bestResult.getLocationAsRectF().height() <= 0 ||
                    bestResult.getLocationAsRectF().left + bestResult.getLocationAsRectF().width() > bitmap.getWidth() ||
                    bestResult.getLocationAsRectF().top + bestResult.getLocationAsRectF().height() > bitmap.getHeight()
            ) fail();

            // Releases model resources if no longer used.
            model.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fail(){
        Toast.makeText(context.getApplicationContext(), "Pas de panneau", Toast.LENGTH_LONG).show();
        rectLocation = null;
    }

    public RectF getRect() throws OcrErrorException {
        return rectLocation;
    }

}
