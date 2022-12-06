package com.example.cse332mainproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registerActivity extends AppCompatActivity {

    EditText email,password,name,number;
    Button signup;

    DatabaseHelper shabdhanDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText)findViewById(R.id.editEmail);
        password = (EditText)findViewById(R.id.editPassword);
        name = (EditText)findViewById(R.id.editName);
        number = (EditText)findViewById(R.id.editPhone);

        signup = (Button) findViewById(R.id.signupButton);

        shabdhanDatabase = new DatabaseHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString();
                String userPass = password.getText().toString();
                String userName = name.getText().toString();
                String userNumber = number.getText().toString();

                if(userEmail.equals("") || userPass.equals("") || userName.equals("") || userNumber.equals("")){
                    Toast.makeText(registerActivity.this,"Please fill up all fields!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean validUser = shabdhanDatabase.checkEmail(userEmail);
                    if(validUser==false){
                        Boolean regCheck = shabdhanDatabase.insertData(userEmail,userPass,userName,userNumber);
                        if(regCheck==true){
                            Toast.makeText(registerActivity.this,"Registration Completed!",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(registerActivity.this,MainActivity.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(registerActivity.this,"Registration Failed. Try again!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(registerActivity.this,"Email already exists!",Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

    }

    public void goBack(View view) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void gotoMain(View view) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
