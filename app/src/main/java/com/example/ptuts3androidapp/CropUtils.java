package com.example.ptuts3androidapp;

import android.graphics.Bitmap;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class CropUtils {

    public Bitmap cropImage(Bitmap src) {
        OpenCVLoader.initDebug();
        Mat test = new Mat();
        Mat testGray = new Mat();
        Mat cannyOutput = new Mat();
        Mat hierarchy = new Mat();
        Utils.bitmapToMat(src, test);
        Imgproc.cvtColor(test, testGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.blur(testGray, testGray, new Size(3,3));
        Imgproc.Canny(testGray,cannyOutput,255/3,255);
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(cannyOutput,contours,hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        MatOfPoint2f[] contoursPoly = new MatOfPoint2f[contours.size()];
        Rect[] boundRect = new Rect[contours.size()];
        for (int i = 0; i < contours.size(); i++) {
            contoursPoly[i] = new MatOfPoint2f();
            Imgproc.approxPolyDP(new MatOfPoint2f(contours.get(i).toArray()), contoursPoly[i], 3 , true);
            boundRect[i] = Imgproc.boundingRect(new MatOfPoint(new MatOfPoint(contoursPoly[i].toArray())));
        }
        int i = findLargestContour(contours);
        Utils.matToBitmap(testGray,src);
        src = Bitmap.createBitmap(src, (int)boundRect[i].x, (int)boundRect[i].y, (int)boundRect[i].width,(int)boundRect[i].height);
        return src;
    }

    private int findLargestContour(List<MatOfPoint> contours) {

        double maxVal = 0;
        int maxValIdx = 0;
        for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++) {
            double contourArea = Imgproc.contourArea(contours.get(contourIdx));
            if (maxVal < contourArea) {
                maxVal = contourArea;
                maxValIdx = contourIdx;
            }
        }
        return maxValIdx;
    }

}
