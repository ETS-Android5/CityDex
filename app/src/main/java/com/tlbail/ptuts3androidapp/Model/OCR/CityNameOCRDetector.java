package com.tlbail.ptuts3androidapp.Model.OCR;

import android.graphics.Bitmap;
import android.graphics.RectF;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class CityNameOCRDetector {

    private TextRecognizer textRecognizer;
    private OcrResultListener ocrResultListener;

    public CityNameOCRDetector(OcrResultListener ocrResultListener){
        this.ocrResultListener = ocrResultListener;
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    }

    public void runOcrResult(Bitmap photo, RectF rectF) {
        InputImage image = InputImage.fromBitmap(SignImage.getImageForOCR(photo, rectF),0);
        textRecognizer.process(image).addOnSuccessListener(
                text -> ocrResultListener.onOcrFinish(correctOCRResult(text.getText()))
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
