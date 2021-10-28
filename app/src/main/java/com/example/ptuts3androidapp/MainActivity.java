package com.example.ptuts3androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //propiétés
    private ImageButton collec_button;
    private ImageButton succes_button;
    private ImageButton reglage_button;
    private ImageButton photo_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Initialisation
     */
    private void initActivity(){
        //lien vers les objets graphiques
        collec_button = (ImageButton) findViewById(R.id.collec_button);
        succes_button = (ImageButton) findViewById(R.id.succes_button);
        reglage_button = (ImageButton) findViewById(R.id.reglage_button);
        photo_button = (ImageButton) findViewById(R.id.photo_button);
        // Initialisation des méthodes click sur bouton

    }


    /**
     * méthode de click sur le bouton Ma Collec'
     */
    private void createOnClickCollecButton(){
        collec_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });
    }


}