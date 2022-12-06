package com.example.cse332mainproject;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import static com.example.cse332mainproject.DatabaseContacts.CONTACTS_TABLE;

public class HomepageActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }

    public void onFindLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(HomepageActivity.this);
        if (ActivityCompat.checkSelfPermission(HomepageActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(HomepageActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        try {
                            Geocoder geocoder = new Geocoder(HomepageActivity.this, Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            String safePhone;
                            String safeMessage = "Emergency SOS test Message. Click here to see current location: http://maps.google.com/?q="+addresses.get(0).getLatitude()+","+addresses.get(0).getLongitude();

                            DatabaseContacts databaseContacts = new DatabaseContacts(HomepageActivity.this);
                            SQLiteDatabase db=databaseContacts.getReadableDatabase();

                            String queryString=" SELECT * FROM " + CONTACTS_TABLE;

                            Cursor cursor = db.rawQuery(queryString,null);
                            cursor.moveToFirst();
                            while (cursor.isAfterLast() == false)
                            {
                                safePhone = cursor.getString(2);
                                if(!safePhone.equals("") && !safeMessage.equals("")){
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(safePhone,null,safeMessage,null,null);
                                    Toast.makeText(getApplicationContext(),"SOS has been sent!",Toast.LENGTH_LONG).show();
                                }
                                cursor.moveToNext();
                            }
                            cursor.close();
                            db.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(HomepageActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    public void onClickSOS(View view) {
        if(ContextCompat.checkSelfPermission(HomepageActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            onFindLocation();
        }else{
            ActivityCompat.requestPermissions(HomepageActivity.this,new String[]{Manifest.permission.SEND_SMS},100);
        }

    }

    public void onClickSafe(View view) {
        if(ContextCompat.checkSelfPermission(HomepageActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            AlertDialog.Builder builder = new AlertDialog.Builder(HomepageActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure?");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendSafeMessage();
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            ActivityCompat.requestPermissions(HomepageActivity.this,new String[]{Manifest.permission.SEND_SMS},100);
        }
    }

    private void sendSafeMessage() {

        DatabaseContacts databaseContacts = new DatabaseContacts(this);

        String safePhone;
        String safeMessage = "I am now safe";

        SQLiteDatabase db=databaseContacts.getReadableDatabase();

        String queryString=" SELECT * FROM " + CONTACTS_TABLE;

        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false)
        {
            safePhone = cursor.getString(2);
            if(!safePhone.equals("") && !safeMessage.equals("")){
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(safePhone,null,safeMessage,null,null);
                Toast.makeText(getApplicationContext(),"Safe has been sent!",Toast.LENGTH_LONG).show();
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            if(requestCode == 44 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                onFindLocation();
            }
            else{
                Toast.makeText(getApplicationContext(),"Permission Denied!",Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Permission Denied!",Toast.LENGTH_LONG).show();
        }


    }

    public void gotoSettings(View view) {
        Intent i = new Intent(this,settingActivity.class);
        startActivity(i);
    }

    public void gotoShop(View view) {
        Intent i = new Intent(this,shopActivity.class);
        startActivity(i);
    }

}