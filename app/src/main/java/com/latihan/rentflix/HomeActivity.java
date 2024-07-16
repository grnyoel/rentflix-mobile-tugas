// HomeActivity.java
package com.latihan.rentflix;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private TextView nameTextView, usernameTextView, genderTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nameTextView = findViewById(R.id.name_text_view);
        usernameTextView = findViewById(R.id.username_text_view);
        genderTextView = findViewById(R.id.gender_text_view);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        String gender = intent.getStringExtra("gender");

        nameTextView.setText(name);
        usernameTextView.setText(username);
        genderTextView.setText(gender);
    }
}
