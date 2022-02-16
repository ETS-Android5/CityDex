package com.tlbail.ptuts3androidapp.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.tlbail.ptuts3androidapp.Model.City.CityLoaders.CityLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.LocalDataLoader.UserPropertyLocalLoader;
import com.tlbail.ptuts3androidapp.Model.User.User;
import com.tlbail.ptuts3androidapp.R;

public class OptionsActivity extends AppCompatActivity {

    public static final String LOCATIONTIMEOUTKEY = "LOCATIONTIMEOUT";
    public static final String VERIFLOCATKEY = "VERIFLOCATKEY";

    private EditText editTextLocationTimeOut;
    private User user;
    private Switch switch1VerifierLocat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametre);
        bindUI();
        user = new User(new UserPropertyLocalLoader(this), new CityLocalLoader(this));

        if(user.containsKey(LOCATIONTIMEOUTKEY)){
            editTextLocationTimeOut.setText(user.get(LOCATIONTIMEOUTKEY));
        }

        if(user.containsKey(VERIFLOCATKEY)){
            if(user.get(OptionsActivity.VERIFLOCATKEY).equals(String.valueOf(false))){
                switch1VerifierLocat.setChecked(false);
            }else {
                switch1VerifierLocat.setChecked(true);
            }

        }
    }

    private void bindUI() {

        findViewById(R.id.buttonAnnuleParametre).setOnClickListener(v -> finish());
        findViewById(R.id.buttonValideParametre).setOnClickListener(v -> validate());
        findViewById(R.id.sourceDuProjetButton).setOnClickListener(v -> openSource());

        editTextLocationTimeOut = findViewById(R.id.editTextNumberLocationTimeOutParametre);
        switch1VerifierLocat = findViewById(R.id.switch1VerifierLocat);
    }

    private void openSource() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/TLBail/PtutS3Android"));
        startActivity(browserIntent);
    }

    private void validate() {
        int valueLocationTimeOut = Integer.parseInt(editTextLocationTimeOut.getText().toString());
        if(!user.containsKey(LOCATIONTIMEOUTKEY))
            user.put(LOCATIONTIMEOUTKEY, String.valueOf(valueLocationTimeOut));
        if(user.containsKey(LOCATIONTIMEOUTKEY) && valueLocationTimeOut != Integer.parseInt(user.get(LOCATIONTIMEOUTKEY)))
            user.put(LOCATIONTIMEOUTKEY, String.valueOf(valueLocationTimeOut));
        user.put(VERIFLOCATKEY, String.valueOf(switch1VerifierLocat.isChecked()));
        Toast.makeText(getApplicationContext(), "Information enregistrée ", Toast.LENGTH_LONG).show();
        finish();
    }

}