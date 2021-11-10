package com.example.ptuts3androidapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TessOCR {

    private TessBaseAPI mTess;
    private String result = "";

    public TessOCR(InputStream dataToCopy) throws IOException {
        mTess = new TessBaseAPI();
        String datapath = Environment.getExternalStorageDirectory() + "/Citydex/";
        String language = "fra";
        createOCRData(dataToCopy, datapath);
        mTess.init(datapath, language);
    }

    public String getOCRResult(Bitmap bitmap) {
        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    mTess.setImage(bitmap);
                    result ="";
                    result = mTess.getUTF8Text();
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
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

    //Methode qui passe en nuance de gris un bitmap
    public Bitmap toGrayscale(Bitmap bmpOriginal)
    {

        //Permet d'enlever les pixels de "couleurs"
        bmpOriginal = bmpOriginal.copy(Bitmap.Config.ARGB_8888 , true);

        /*for (int i = 0; i < bmpOriginal.getWidth(); i++){
            for(int j = 0; j < bmpOriginal.getHeight(); j++){
                    int c = bmpOriginal.getPixel(i,j);
                    if(Color.red(c) > 100 || Color.blue(c) > 100 || Color.green(c) > 100){
                        bmpOriginal.setPixel(i,j,Color.rgb(255,255,255));
                    }
            }
        }*/

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
    public Bitmap cropImage(Bitmap src, RectF rect) {
        Bitmap dest = src.createBitmap(src, (int) rect.top, (int) rect.left, (int) rect.width(), (int) rect.height());
        return dest;
    }
}
