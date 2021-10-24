package com.example.ptuts3androidapp;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;

public class TessOCR {

    private TessBaseAPI mTess;
    private String result;

    public TessOCR() {
        mTess = new TessBaseAPI();
        String datapath = Environment.getExternalStorageDirectory() + "/tesseract/";
        String language = "fra";
        File dir = new File(datapath + "tessdata/");
        if (!dir.exists())
            dir.mkdirs();
        Log.i("message", dir.getAbsolutePath());
        mTess.init(datapath, language);
    }

    public String getOCRResult(Bitmap bitmap) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mTess.setImage(bitmap);
                result = mTess.getUTF8Text();
            }
        });
        return result;
    }

    public void onDestroy() {
        if (mTess != null)
            mTess.end();
    }

}
