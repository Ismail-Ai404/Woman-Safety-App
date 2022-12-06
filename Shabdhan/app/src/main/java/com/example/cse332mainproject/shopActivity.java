package com.example.cse332mainproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class shopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
    }

    public void goBack(View view) {
        Intent i = new Intent(this,HomepageActivity.class);
        startActivity(i);
    }

    public void pepperSpray(View v) {
        // TODO Auto-generated method stub

        Uri uri = Uri.parse("https://bangladesh.desertcart.com/products/75845687-impower-self-defence-green-chilli-spray-50-g");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void taser(View v) {
        // TODO Auto-generated method stub

        Uri uri = Uri.parse("https://www.facebook.com/selfdefencebd123/?ref=page_internal");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void alarmWhistle(View v) {
        // TODO Auto-generated method stub

        Uri uri = Uri.parse("https://bangladesh.desertcart.com/products/120665950-kosin-safe-sound-personal-alarm-6-pack-140-db-personal-security-alarm-keychain-with-led-lights-emergency-safety-alarm-for-women-men-children-elderly");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}