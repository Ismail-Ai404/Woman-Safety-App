package com.example.cse332mainproject;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context,"LoginInfo.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase shabdhanDatabase) {
        shabdhanDatabase.execSQL("create Table users(email Text primary key, password Text,name Text,number Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase shabdhanDatabase, int i, int i1) {
        shabdhanDatabase.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String email,String password,String name, String number){
        SQLiteDatabase shabdhanDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("name",name);
        contentValues.put("number",number);
        long result = shabdhanDatabase.insert("users",null,contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public Boolean checkEmail(String email){
        SQLiteDatabase shabdhanDatabase = this.getWritableDatabase();
        Cursor cursor = shabdhanDatabase.rawQuery("select * from users where email = ?",new String[] {email});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean checkEmailPassword(String email,String password){
        SQLiteDatabase shabdhanDatabase = this.getWritableDatabase();
        Cursor cursor = shabdhanDatabase.rawQuery("select * from users where email = ? and password = ?",new String[] {email,password});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }

}
