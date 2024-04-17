package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelecomManager tm2 = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);

        checkPermission(Manifest.permission.READ_PHONE_STATE, 100);

        // App title
        TextView textBox0 = findViewById(R.id.textView0);
        String txt0 = "ICCID Reader";
        textBox0.setTextSize(32);
        textBox0.setText(txt0);

        // Getting all CallCapablePhoneAccounts
        List<PhoneAccountHandle> phoneAccountHandle = tm2.getCallCapablePhoneAccounts();

        /* Textboxes ICCIDs */
        // ICCID SIM1, if present
        TextView textBox = findViewById(R.id.textView1);
        textBox.setTextSize(22);
        String txt = phoneAccountHandle.get(0).getId();
        if(!txt.isEmpty()){
            textBox.setText("ICCID SIM1:\n" + txt);
        }
        else{
            textBox.setText("-");
        }


        // ICCID SIM2, if present
        TextView textBox2 = findViewById(R.id.textView2);
        textBox2.setTextSize(22);
        String txt2 = phoneAccountHandle.get(1).getId();
        if(!txt2.isEmpty()){
            textBox2.setText("ICCID SIM2:\n" + txt2);
        }
        else{
            textBox2.setText("-");
        }

    }


    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

}