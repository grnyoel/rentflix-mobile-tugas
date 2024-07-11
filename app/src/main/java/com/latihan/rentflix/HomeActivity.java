package com.latihan.rentflix;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.latihan.rentflix.DatabaseHelper;


public class HomeActivity extends AppCompatActivity {

    private TextView nameTextView, usernameTextView, genderTextView;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        nameTextView = findViewById(R.id.name_text_view);
        usernameTextView = findViewById(R.id.username_text_view);
        genderTextView = findViewById(R.id.gender_text_view);

        displayUserData();
    }

    private void displayUserData() {
        String[] projection = {
                DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_USERNAME,
                DatabaseHelper.COLUMN_GENDER
        };

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_USER,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERNAME));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GENDER));

            nameTextView.setText("Name: " + name);
            usernameTextView.setText("Username: " + username);
            genderTextView.setText("Gender: " + gender);

            cursor.close();
        } else {
            Toast.makeText(this, "No user data found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
