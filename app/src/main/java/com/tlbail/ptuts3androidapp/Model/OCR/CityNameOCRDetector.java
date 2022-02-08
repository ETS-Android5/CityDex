package com.tlbail.ptuts3androidapp.Model.OCR;

import android.graphics.RectF;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.tlbail.ptuts3androidapp.Model.PanneauVersVille.PhotoToCity;

public class CityNameOCRDetector {

    private TextRecognizer textRecognizer;
    private PhotoToCity photoToCity;

    public CityNameOCRDetector(PhotoToCity photoToCity){
        this.photoToCity = photoToCity;
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    }

    public void runOcrResult(RectF rectF) {
        photoToCity.setPhotoToTransform(SignImage.getImageForOCR(photoToCity.getPhotoToTransform(), rectF));
        InputImage image = InputImage.fromBitmap(photoToCity.getPhotoToTransform(),0);
        textRecognizer.process(image).addOnSuccessListener(
                text -> photoToCity.onOcrFinish(correctOCRResult(text.getText()))
        );
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
