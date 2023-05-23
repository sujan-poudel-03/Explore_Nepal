package com.example.explorenepal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;



public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "explore_nepal_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_DESCRIPTION = "description";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_IMAGE + " BLOB, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT)";
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropQuery);
        onCreate(db);
    }

    public boolean addInsert(String name, String category, byte[] image, String phone, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_IMAGE, image);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_DESCRIPTION, description);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

//    public boolean addInsert(String name, String category, byte[] image, String phone, String description) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String insertQuery = "INSERT INTO " + TABLE_NAME + " (name, category, image, phone, description) VALUES (?, ?, ?, ?, ?)";
//        SQLiteStatement statement = db.compileStatement(insertQuery);
//
//        statement.bindString(1, name);
//        statement.bindString(2, category);
//        statement.bindBlob(3, image);
//        statement.bindString(4, phone);
//        statement.bindString(5, description);
//
//        long result = statement.executeInsert();
//        return result != -1; // Returns true if insertion is successful, false otherwise
//    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(selectQuery, null);
    }

    public boolean updateData(int id, String name, String category, byte[] image, String phone, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_IMAGE, image);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_DESCRIPTION, description);

        String selection = "id = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        int rowsAffected = db.update(TABLE_NAME, values, selection, selectionArgs);
        return rowsAffected > 0;
    }

    public boolean deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        return rowsAffected > 0;
    }
}
