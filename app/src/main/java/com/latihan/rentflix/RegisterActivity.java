package com.latihan.rentflix;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private RadioGroup genderRadioGroup;
    private Button registerButton;

    private UserDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        nameEditText = findViewById(R.id.name_edit_text);
        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        genderRadioGroup = findViewById(R.id.gender_radio_group);
        registerButton = findViewById(R.id.register_button);

        dataSource = new UserDataSource(this);
        dataSource.open();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        findViewById(R.id.sign_in_text_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLogin();
            }
        });
    }

    private void registerUser() {
        String name = nameEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || selectedGenderId == -1) {
            Toast.makeText(this, "Please fill all fields and select a gender", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        String gender = getGenderFromId(selectedGenderId);
        long result = dataSource.insertUser(name, username, gender);

        if (result != -1) {
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            navigateToLogin();
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }

    private String getGenderFromId(int genderId) {
        if (genderId == R.id.radio_male) {
            return "Male";
        } else if (genderId == R.id.radio_female) {
            return "Female";
        } else {
            return "Other";
        }
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onSignInClicked(View view) {
        navigateToLogin();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}
