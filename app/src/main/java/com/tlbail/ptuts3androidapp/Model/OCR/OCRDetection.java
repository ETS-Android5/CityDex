package com.tlbail.ptuts3androidapp.Model.OCR;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.os.Environment;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.tlbail.ptuts3androidapp.Model.PanneauVersVille.PhotoToCity;

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

    public void runOcrResult(PhotoToCity photoToCity, RectF rectF) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                    photoToCity.setBitmap(cropImage(photoToCity.getBitmap(), rectF));
                    photoToCity.setBitmap(new SignImage().getImageForOCR(photoToCity.getBitmap()));

                    mTess.setImage(photoToCity.getBitmap());
                    result = mTess.getUTF8Text();
                    System.out.println("Résultat OCR = " + result);
                    result = result.replace("|", "I");
                    result = result.replaceAll("ST ", "SAINT ");
                    result = result.replaceAll("[^a-zA-ZÉÈ -]", "");
                    result = result.replaceAll("^ ", "");
                    result = result.replaceAll(" $", "");
                    result = result.replace(" - ", "-");

                    photoToCity.setOcrResult(result);
                    onDestroy();
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

    //Méthode qui crop un bitmap
    public Bitmap cropImage(Bitmap src, RectF rect) {
        Bitmap dest = src.createBitmap(src, (int) Math.max(0, rect.left), (int) rect.top, (int) rect.width(), (int) rect.height());
        return dest;
    }
}
