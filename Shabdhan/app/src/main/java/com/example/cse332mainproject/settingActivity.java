package com.example.cse332mainproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class settingActivity extends AppCompatActivity implements DialogBox.DialogBoxListener {

    DatabaseContacts db;

    ArrayList<String> listItem;
    ArrayAdapter adapter;
    Button logoutButton;
    Button deleteButton;
    Button refreshButton;
    Button addButton;
    ListView numberList;
    int index;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        db= new DatabaseContacts(this);
        deleteButton = (Button)findViewById(R.id.button6);
        listItem = new ArrayList<>();
        logoutButton = (Button)findViewById(R.id.button);
        numberList = findViewById(R.id.numbersListView);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        viewData();

        numberList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                index = i;

            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                finish();
                Intent i = new Intent(settingActivity.this,MainActivity.class);
                startActivity(i);
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String n = listItem.get(index);
                SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("contacts.db", Context.MODE_PRIVATE,null);
                Cursor c=simpledb.rawQuery("SELECT * FROM CONTACTS_TABLE WHERE CONTACT_NUMBER ='"+ n+"'", null);
                if(c.moveToFirst())
                {
                    simpledb.execSQL("DELETE FROM CONTACTS_TABLE WHERE CONTACT_NUMBER ='"+ n+"'");
                }

                listItem.clear();
                viewData();
            }
        });

        refreshButton = (Button)findViewById(R.id.button5);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listItem.clear();
                viewData();

            }
        });

        addButton = (Button)findViewById(R.id.button7);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

    }

    public void openDialog()
    {
        DialogBox dialogBox = new DialogBox();
        dialogBox.show(getSupportFragmentManager(), "Dialog Box");

    }

    @Override
    public void applyTexts(String username, String password) {

        db= new DatabaseContacts(this);
        Contacts contacts = new Contacts(-1,username,password,true);
        db.addOne(contacts);

        listItem.clear();
        viewData();
    }

    private void viewData() {

        Cursor cursor = db.getNumbers();

        if(cursor.getCount()==0){
            Toast.makeText(this,"No data to show!",Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                listItem.add(cursor.getString(2));
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listItem);
            numberList.setAdapter(adapter);
        }

    }

    public void goBack(View view) {
        Intent i = new Intent(this,HomepageActivity.class);
        startActivity(i);
    }

    public void refreshList(View view){
        listItem.clear();
        viewData();
    }




}