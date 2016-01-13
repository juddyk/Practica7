package com.example.juddyreina.practica7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="ContactManager",
            TABLE_CONTACT="contacto",
            KEY_ID="id",
            KEY_NAME="name",
            KEY_PASS="pass",
            KEY_STATE="false";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_CONTACT + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_PASS + " TEXT,"
                + KEY_STATE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        onCreate(db);

    }

    public void createContact (usuarios contact){
        SQLiteDatabase db= getWritableDatabase();
        ContentValues values= new ContentValues();

        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PASS, contact.getPassw());
        values.put(KEY_STATE,contact.getState());
        db.insert(TABLE_CONTACT, null, values);
        db.close();

    }

    public void changeState(usuarios usr){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACT, null);

    }

    public void seeState(usuarios usr){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACT, null);
    }

    public int getContactsCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACT, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

    public List<usuarios> getAllContacts() {
        List<usuarios> contacts = new ArrayList<usuarios>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACT, null);

        if (cursor.moveToFirst()) {
            do {
                contacts.add(new usuarios(cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }

}
