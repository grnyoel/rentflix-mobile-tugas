package com.latihan.rentflix;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.latihan.rentflix.DatabaseHelper;


import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView signUpTextView;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginButton = findViewById(R.id.login_button);
        signUpTextView = findViewById(R.id.sign_up_now_text_view);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToRegister();
            }
        });
    }

    private void loginUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isValidUser = checkUserCredentials(username, password);

        if (isValidUser) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            navigateToHome();
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkUserCredentials(String username, String password) {
        String[] projection = { DatabaseHelper.COLUMN_USERNAME, DatabaseHelper.COLUMN_PASSWORD };
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { username, password };

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_USER,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean isValid = cursor != null && cursor.moveToFirst();

        if (cursor != null) {
            cursor.close();
        }

        return isValid;
    }

    private void navigateToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
