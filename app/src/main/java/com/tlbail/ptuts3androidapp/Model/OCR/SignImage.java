package com.tlbail.ptuts3androidapp.Model.OCR;

import android.graphics.Bitmap;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class SignImage {

    public SignImage(){

    }

    public Bitmap getGrayscaleCroppedSign(Bitmap src) {
        OpenCVLoader.initDebug();
        Mat test = new Mat();
        Mat grayscale;
        Mat cannyOutput;
        Utils.bitmapToMat(src, test);
        grayscale = grayscale(test);
        cannyOutput = canny(grayscale);
        Utils.matToBitmap(cannyOutput,src);
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
}
