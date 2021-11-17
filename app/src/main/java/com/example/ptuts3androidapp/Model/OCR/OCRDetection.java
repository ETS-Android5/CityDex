package com.example.ptuts3androidapp.Model.OCR;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Environment;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class OCRDetection {

    private TessBaseAPI mTess;
    private String result = "";

    public OCRDetection(InputStream dataToCopy) throws IOException {
        mTess = new TessBaseAPI();
        String datapath = Environment.getExternalStorageDirectory() + "/Citydex/";
        String language = "fra";
        createOCRData(dataToCopy, datapath);
        mTess.init(datapath, language);
    }

    public void runOcrResult(OcrResultListener ocrResultListener, Bitmap bitmap) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mTess.setImage(bitmap);
                result ="";
                result = mTess.getUTF8Text();
                ocrResultListener.onOcrFinish(result);
            }
        }).start();
    }

    public void onDestroy() {
        if (mTess != null)
            mTess.end();
    }

    private void copy(InputStream in, File dst) throws IOException {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
    }


    private void createOCRData(InputStream dataToCopy, String datapath) throws IOException {
        File dir = new File(datapath + "tessdata/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File data = new File(dir.getAbsolutePath(), "fra.traineddata");
        if (!data.exists()){
            copy(dataToCopy, data);
        }
    }

    //TODO remove to grayscale and crop image and use crop image from cropUtils

    //Methode qui passe en nuance de gris un bitmap
    public Bitmap toGrayscale(Bitmap bmpOriginal)
    {

        //Permet d'enlever les pixels de "couleurs"
        bmpOriginal = bmpOriginal.copy(Bitmap.Config.ARGB_8888 , true);

        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    //Méthode qui crop un bitmap
    public Bitmap cropImage(Bitmap src, RectF rect) throws OcrErrorException {
        if(rectIsNotCorrect(rect, src)) throw new OcrErrorException();
        Bitmap dest = src.createBitmap(src, (int) rect.top, (int) rect.left, (int) rect.width(), (int) rect.height());
        return dest;
    }

    private boolean rectIsNotCorrect(RectF rect, Bitmap src) {
        return rect == null || rect.top < 0 || rect.left < 0 || rect.left + rect.height()  > src.getHeight() || rect.top + rect.width() > src.getWidth();
    }
}
