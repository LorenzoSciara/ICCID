package iccid.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {

    private CountDownLatch permissionLatch;
    private static final int PHONE_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textViewICCID = findViewById(R.id.ICCID);
        String ICCID = "Error!";

        TelecomManager tm2 = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);

        String pa = Manifest.permission.READ_PHONE_STATE;
        String pn = String.valueOf(PackageManager.PERMISSION_GRANTED);


        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            // L'autorizzazione è stata concessa
        } else {
            // L'autorizzazione non è stata concessa, richiedila all'utente
            permissionLatch = new CountDownLatch(1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
            try {
                // Blocca l'applicazione fino a quando l'autorizzazione non viene concessa o negata
                permissionLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        checkPermission(Manifest.permission.READ_PHONE_STATE, PHONE_PERMISSION_CODE);
        List<PhoneAccountHandle> phoneAccountHandle = tm2.getSelfManagedPhoneAccounts();

        SubscriptionManager sm = (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
        List<SubscriptionInfo> sis = sm.getAccessibleSubscriptionInfoList();

        SubscriptionInfo si = sis.get(0);
        String iccID = si.getIccId();

        if(!phoneAccountHandle.isEmpty()){
            ICCID=phoneAccountHandle.get(0).toString();
        }
        else{
            ICCID="No SIMs are present";
        }
        textViewICCID.setText(ICCID);
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // L'utente ha concesso l'autorizzazione, puoi eseguire le operazioni necessarie
                // Segnala al CountDownLatch che l'autorizzazione è stata concessa
                permissionLatch.countDown();
            } else {
                // L'utente ha negato l'autorizzazione, gestisci di conseguenza
                // Segnala al CountDownLatch che l'autorizzazione è stata negata
                permissionLatch.countDown();
            }
        }
    }*/

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PHONE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Phone Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(MainActivity.this, "Phone Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }
    }

}