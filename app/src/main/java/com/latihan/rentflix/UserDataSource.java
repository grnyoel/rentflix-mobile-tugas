package com.latihan.rentflix;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.latihan.rentflix.DatabaseHelper;


public class UserDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public UserDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertUser(String name, String username, String gender) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_USERNAME, username);
        values.put(DatabaseHelper.COLUMN_GENDER, gender);

        return database.insert(DatabaseHelper.TABLE_USER, null, values);
    }
}

