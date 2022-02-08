package com.tlbail.ptuts3androidapp.Model.OCR;

import android.graphics.Bitmap;
import android.graphics.RectF;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;

public class SignImage {

    public static Bitmap getImageForOCR(Bitmap src, RectF rect) {
        if(rect != null)
            src = cropImage(src, rect);
        return processBitmap(src);
    }
    private static Bitmap processBitmap(Bitmap src) {
        OpenCVLoader.initDebug();
        Mat test = new Mat();
        Utils.bitmapToMat(src, test);
        Mat grayscale = grayscale(test);
        Photo.fastNlMeansDenoising(grayscale, grayscale);
        Utils.matToBitmap(grayscale,src);
        return src;
    }

    private static Mat grayscale(Mat mat){
        Mat grayscale = new Mat();
        Imgproc.cvtColor(mat, grayscale, Imgproc.COLOR_BGR2GRAY);
        Imgproc.blur(grayscale, grayscale, new Size(3,3));
        return grayscale;
    }

    private static Bitmap cropImage(Bitmap src, RectF rect) {
        Bitmap dest = src.createBitmap(src, (int) Math.max(0, rect.left), (int) rect.top, (int) rect.width(), (int) rect.height());
        return dest;
    }
}
