package com.example.cse332mainproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

public class SecondSettings extends AppCompatActivity {

    Button btn_add,btn_viewAll;
    Switch sw_active;
    EditText et_phonenumber,et_name;
    ListView lv_contacts;

    DatabaseContacts databaseContacts;

    ArrayAdapter<Contacts> contactArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_settings);

        btn_add=findViewById(R.id.btn_add);
        btn_viewAll=findViewById(R.id.btn_viewAll);
        et_phonenumber= findViewById(R.id.et_phonenumber);
        et_name=findViewById(R.id.editTextTextPersonName);
        sw_active=findViewById(R.id.sw_active);
        lv_contacts=findViewById(R.id.lv_contacts);

        databaseContacts=new DatabaseContacts(SecondSettings.this);

        ShowContactsOnListView(databaseContacts);

        btn_add.setOnClickListener(v -> {

            Contacts con;
            try{
                con=new Contacts(-1,et_name.getText().toString(),et_phonenumber.getText().toString(),sw_active.isChecked());
                Toast.makeText(SecondSettings.this,"Contact added",Toast.LENGTH_SHORT).show();

            }
            catch (Exception e){
                Toast.makeText(SecondSettings.this,"Error creating contact",Toast.LENGTH_SHORT).show();
                con=new Contacts( -1,"Error","Error",false);
            }
            DatabaseContacts databaseContacts=new DatabaseContacts(SecondSettings.this);

            boolean success=databaseContacts.addOne(con);
            ShowContactsOnListView(databaseContacts);

        });

        btn_viewAll.setOnClickListener((v) ->{

            databaseContacts =new DatabaseContacts(SecondSettings.this);
            //Toast.makeText(MainActivity.this,everyone.toString(),Toast.LENGTH_SHORT).show();

            ShowContactsOnListView(databaseContacts);

        });

    }

    private void ShowContactsOnListView(DatabaseContacts databaseContacts2) {
        contactArrayAdapter = new ArrayAdapter<>(SecondSettings.this, android.R.layout.simple_list_item_1, databaseContacts2.getEveryone());
        lv_contacts.setAdapter(contactArrayAdapter);
    }

    public void goBack(View view) {
        Intent i = new Intent(this,settingActivity.class);
        startActivity(i);
    }
}