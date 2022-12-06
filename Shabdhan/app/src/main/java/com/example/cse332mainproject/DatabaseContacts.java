package com.example.cse332mainproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DatabaseContacts extends SQLiteOpenHelper {
    public static final String CONTACTS_TABLE="CONTACTS_TABLE";
    public static final String COLUMN_CONTACT_NAME = "CONTACT_NAME";
    public static final String COLUMN_CONTACT_NUMBER = "CONTACT_NUMBER";
    public static final String COLUMN_ACTIVE_CONTACT = "ACTIVE_CONTACT";
    public static final String COLUMN_ID = "ID";

    public DatabaseContacts(@Nullable Context context) {
        super(context, "contacts.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement="CREATE TABLE "+CONTACTS_TABLE+ "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CONTACT_NAME + " TEXT," + COLUMN_CONTACT_NUMBER + " TEXT," + COLUMN_ACTIVE_CONTACT + " BOOL)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(Contacts contacts){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_CONTACT_NAME,contacts.getName());
        cv.put(COLUMN_CONTACT_NUMBER,contacts.getNumber());
        cv.put(COLUMN_ACTIVE_CONTACT,contacts.isActive());

        long insert = db.insert(CONTACTS_TABLE, null, cv);
        if(insert==-1){
            return false;
        }
        return true;
    }
    public List<Contacts> getEveryone(){
        List<Contacts> returnList=new ArrayList<>();

        String queryString=" SELECT * FROM " + CONTACTS_TABLE;

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            //loop through results
            do{
                int contactID=cursor.getInt(0);
                String contactName= cursor.getString(1);
                String contactNumber=cursor.getString(2);
                boolean contactActive=cursor.getInt(3)== 1 ? true: false;

                Contacts newContact=new Contacts(contactID,contactName,contactNumber,contactActive);
                returnList.add(newContact);
            }while(cursor.moveToNext());

        }else{

        }
        cursor.close();
        db.close();
        return returnList;
    }

    public Cursor getNumbers(){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + CONTACTS_TABLE,null);

        return cursor;

    }
}
