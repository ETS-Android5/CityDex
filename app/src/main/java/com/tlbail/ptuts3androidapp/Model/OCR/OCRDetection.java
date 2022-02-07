package com.tlbail.ptuts3androidapp.Model.OCR;

import android.graphics.Bitmap;
import android.graphics.RectF;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.tlbail.ptuts3androidapp.Model.PanneauVersVille.PhotoToCity;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class OCRDetection {

    private TextRecognizer textRecognizer;

    public OCRDetection() throws IOException {
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    }

    public void runOcrResult(PhotoToCity photoToCity, RectF rectF) {
        photoToCity.setBitmap(new SignImage().getImageForOCR(photoToCity.getBitmap(), rectF));
        InputImage image = InputImage.fromBitmap(photoToCity.getBitmap(),0);
        textRecognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
            @Override
            public void onSuccess(Text text) {
                String result = text.getText();
                result = correctOCRResult(result);
                photoToCity.setOcrResult(result);
            }
        });
    }

    private String correctOCRResult(String result) {
        result = result.replace("|", "I");
        result = result.replaceAll("ST ", "SAINT ");
        result = result.replaceAll("[^a-zA-ZÉÈ -]", "");
        result = result.replaceAll("^ ", "");
        result = result.replaceAll(" $", "");
        result = result.replace(" - ", "-");
        return result;
    }
}
