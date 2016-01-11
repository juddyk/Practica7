package com.example.juddyreina.practica7;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="registroManager",
            TABLE_REGISTRO="Registro",
            KEY_ID="id",
            KEY_NAME="name",
            KEY_PASS="password";

    public DataBaseHandler(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_REGISTRO + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT," + KEY_PASS + " TEXT");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRO);
        onCreate(db);
    }

    public void createRegistro (usuarios regis){
        SQLiteDatabase db= getWritableDatabase();
        ContentValues values= new ContentValues();

        values.put(KEY_NAME, regis.getName());
        values.put(KEY_PASS, regis.getPassw());

        db.insert(TABLE_REGISTRO, null, values);
        db.close();

    }

}
