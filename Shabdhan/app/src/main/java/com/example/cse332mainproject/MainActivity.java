package com.example.cse332mainproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity

{
    EditText username,password;
    ImageButton btnLogin;
    SharedPreferences sharedPreferences;
    DatabaseHelper myDB;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.editTextTextEmailAddress);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        btnLogin = (ImageButton) findViewById(R.id.imageButton);

        myDB = new DatabaseHelper(this);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String name = sharedPreferences.getString(KEY_EMAIL,null);
        if(name!=null){
            Intent intent = new Intent(MainActivity.this,HomepageActivity.class);
            startActivity(intent);
        }



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("") || pass.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please enter the credentials", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean result = myDB.checkEmailPassword(user, pass);
                    if(result)
                    {
                        String man;

                        SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("LoginInfo.db", Context.MODE_PRIVATE,null);

                        Cursor c=simpledb.rawQuery("select * from users where email=?",new String[]{username.getText().toString()});
                        c.moveToFirst();
                        man = c.getString(2);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_EMAIL,user);
                        editor.putString(KEY_PASSWORD,pass);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                        intent.putExtra("NameKey",man);
                        startActivity(intent);

                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }


    public void gotoRegistration(View view) {
        Intent i = new Intent(this,registerActivity.class);
        startActivity(i);
    }


}



