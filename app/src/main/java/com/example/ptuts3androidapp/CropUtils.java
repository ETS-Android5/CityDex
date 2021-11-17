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
        Mat grayscale;
        Mat cannyOutput;
        Utils.bitmapToMat(src, test);
        grayscale = grayscale(test);
        cannyOutput = canny(grayscale);
        Rect rect = getSign(cannyOutput);
        Utils.matToBitmap(grayscale,src);
        src = Bitmap.createBitmap(src, (int)rect.x, (int)rect.y, (int)rect.width,(int)rect.height);
        return src;
    }

    private Mat grayscale(Mat mat){
        Mat grayscale = new Mat();
        Imgproc.cvtColor(mat, grayscale, Imgproc.COLOR_BGR2GRAY);
        Imgproc.blur(grayscale, grayscale, new Size(3,3));
        return grayscale;
    }

    private Mat canny(Mat mat){
        Mat cannyOutput = new Mat();
        Imgproc.Canny(mat,cannyOutput,255/3,255);
        return cannyOutput;
    }

    private Rect getSign(Mat mat){
        Mat hierarchy = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(mat,contours,hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        MatOfPoint2f[] contoursPoly = new MatOfPoint2f[contours.size()];
        Rect[] boundRect = new Rect[contours.size()];
        for (int i = 0; i < contours.size(); i++) {
            addRect(contours, contoursPoly, boundRect, i);
        }
        int i = findLargestContour(contours);
        return new Rect(boundRect[i].x, boundRect[i].y, boundRect[i].width, boundRect[i].height);
    }

    private void addRect(List<MatOfPoint> contours, MatOfPoint2f[] contoursPoly, Rect[] boundRect, int i) {
        contoursPoly[i] = new MatOfPoint2f();
        Imgproc.approxPolyDP(new MatOfPoint2f(contours.get(i).toArray()), contoursPoly[i], 3 , true);
        boundRect[i] = Imgproc.boundingRect(new MatOfPoint(new MatOfPoint(contoursPoly[i].toArray())));
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

    public double similarity(String s1, String s2){
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2; shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; /* both strings are zero length */ }
        return (longerLength - levenshtein(longer, shorter)) / (double) longerLength;
    }

    private int levenshtein(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }

}
