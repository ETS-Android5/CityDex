package com.example.ptuts3androidapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ptuts3androidapp.ml.MobilenetV110224Quant;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonSelectFile;
    private Button buttonPredict;
    private TextView textViewResult;
    private Bitmap bitmap;
    private ImageView imageView;
    private List<String> objectypes;
    private Button takePhotoButton;

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        bindUi();


        String filename = "labels.txt";
        try {
            InputStream inputStream = getApplication().getAssets().open(filename);
            String newLine = System.getProperty("line.separator");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            objectypes = new ArrayList<String>();
            for (String line; (line = reader.readLine()) != null; ) {
                objectypes.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private void bindUi() {
        buttonSelectFile = findViewById(R.id.selectFileButton);
        buttonSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });
        buttonPredict = findViewById(R.id.buttonDecrypte);
        buttonPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPredictClick();
            }
        });
        textViewResult = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        takePhotoButton = findViewById(R.id.buttonTakePhoto);
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ontakePhotoClick();
            }
        });
    }


    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    handleTheReturnedUri(result);
                }
            });

    private void handleTheReturnedUri(Uri result) {
        System.out.println("result = " + result);
        imageView.setImageURI(result);
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void onPredictClick() {
        bitmap = Bitmap.createScaledBitmap(bitmap, 224,224, true);
        predictBitmap(bitmap);
    }


    private void ontakePhotoClick(){
        TextureView textureView = findViewById(R.id.textureView);
        bitmap = textureView.getBitmap();
    }

    private void predictBitmap(Bitmap bitmapToProcess){
        try {
            MobilenetV110224Quant model = MobilenetV110224Quant.newInstance(this);

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);


            inputFeature0.loadBuffer(TensorImage.fromBitmap(bitmapToProcess).getBuffer());

            // Runs model inference and gets result.
            MobilenetV110224Quant.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            displayData(outputFeature0);
            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }

    }

    private void displayData(TensorBuffer outputFeature0) {

        int maxIndex = getMaxId(outputFeature0.getFloatArray());

        textViewResult.setText(objectypes.get(maxIndex));

    }

    private int getMaxId(float[] tab){
        int idmax = 0;
        for (int i = 1; i < tab.length; i++) {
            if(tab[idmax] < tab[i]){
                idmax = i;
            }
        }
        return idmax;
    }
}