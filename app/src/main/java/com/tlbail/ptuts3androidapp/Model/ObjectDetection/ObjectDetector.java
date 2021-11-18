package com.tlbail.ptuts3androidapp.Model.ObjectDetection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.tlbail.ptuts3androidapp.Model.OCR.OcrErrorException;
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


    public String getResultDataInText() {
        String resultText = "";
        for (Detection detection : result){
            resultText += "position de l'objet : (" + detection.getBoundingBox().left + " | " +
                    detection.getBoundingBox().top + ") | (" + detection.getBoundingBox().right + " | " +
                    detection.getBoundingBox().bottom + " ) ";
            resultText += "\nje pense que c'est : ";
            for (Category category: detection.getCategories()){
                resultText += " - " + category.getLabel() + "\n";
            }
        }
        return resultText;
    }

    public RectF getRect() throws OcrErrorException {
        Rect r = new Rect();
        if(result.size() <= 0) throw new OcrErrorException();
        r.set((int)result.get(0).getBoundingBox().left, (int)result.get(0).getBoundingBox().top,
                (int)result.get(0).getBoundingBox().right, (int)result.get(0).getBoundingBox().bottom);

        Log.i("LEFT_Rect", "" + result.get(0).getBoundingBox().left);
        Log.i("TOP_Rect", "" + result.get(0).getBoundingBox().top);
        Log.i("RIGHT_Rect", "" + result.get(0).getBoundingBox().right);
        Log.i("BOTTOM_Rect", "" + result.get(0).getBoundingBox().bottom);

        return new RectF(r);
    }

}
