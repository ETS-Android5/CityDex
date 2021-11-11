package com.example.ptuts3androidapp.Model.DetectionTextPanneau;

public class ResultScan {

    private String objectDetectionTextResult;
    private String ocrDetectionTextResult;

    public ResultScan(String objectDetectionTextResult, String ocrDetectionTextResult) {
        this.objectDetectionTextResult = objectDetectionTextResult;
        this.ocrDetectionTextResult = ocrDetectionTextResult;
    }

    public String getObjectDetectionTextResult() {
        return objectDetectionTextResult;
    }

    public String getOcrDetectionTextResult() {
        return ocrDetectionTextResult;
    }

}
