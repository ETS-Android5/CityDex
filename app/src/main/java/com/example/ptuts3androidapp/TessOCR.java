package com.example.ptuts3androidapp;

import android.graphics.Bitmap;
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
        String datapath = Environment.getExternalStorageDirectory() + "/tesseract/";
        String language = "fra";
        File dir = new File(datapath + "tessdata/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File data = new File(dir.getAbsolutePath(), "fra.traineddata");
        if (!data.exists()){
            copy(dataToCopy, data);
        }
        mTess.init(datapath, language);
    }

    public String getOCRResult(Bitmap bitmap) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                mTess.setImage(bitmap);
                result = mTess.getUTF8Text();
            }
        });
        t.start();
        t.join();
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

}
