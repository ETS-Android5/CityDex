package com.tlbail.ptuts3androidapp.Model.OCR;

import android.graphics.Bitmap;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;

public class SignImage {

    public SignImage(){}

    public Bitmap getImageForOCR(Bitmap src) {
        OpenCVLoader.initDebug();
        Mat test = new Mat();
        Utils.bitmapToMat(src, test);
        Mat grayscale = grayscale(test);
        Photo.fastNlMeansDenoising(grayscale, grayscale);
        Utils.matToBitmap(grayscale,src);
        return src;
    }

    private Mat grayscale(Mat mat){
        Mat grayscale = new Mat();
        Imgproc.cvtColor(mat, grayscale, Imgproc.COLOR_BGR2GRAY);
        Imgproc.blur(grayscale, grayscale, new Size(3,3));
        return grayscale;
    }
}
