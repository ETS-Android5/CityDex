package com.example.ptuts3androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private ImageView imgView;
    private ImageView imgGray;
    private TextView textView;

    private String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    private int permCode = 23;

    private TessOCR ocr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        imgGray = findViewById(R.id.imgGray);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perms,permCode);
        }

        ocr = null;
        try {
            ocr = new TessOCR(getAssets().open("fra.traineddata"));
    } catch (IOException e) {
            e.printStackTrace();
        }
        File dir = new File(Environment.getExternalStorageDirectory() + "/Citydex/images");
        if (!dir.exists()){
            dir.mkdirs();
        }
        File file = new File(Environment.getExternalStorageDirectory() + "/Citydex/images/image_ocr.jpg");
        try {
            copy(getAssets().open("drawable/image_ocr.jpg") ,file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());

        Bitmap bitmap_gray = new CropUtils().cropImage(bitmap);

       // bitmap_gray = cropImage(bitmap_gray, new Rect(0,0,500,200));

        imgGray.setImageBitmap(bitmap_gray);

        String res = ocr.getOCRResult(bitmap_gray);
        res = res.replace('|','I');
        res = res.replaceAll("[^a-zA-Z-]","");
        textView.setText(res+" CHAUZON : "+similiraty(res, "CHAUZON"));


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ocr.onDestroy();
    }


    private double similiraty(String s1, String s2){
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