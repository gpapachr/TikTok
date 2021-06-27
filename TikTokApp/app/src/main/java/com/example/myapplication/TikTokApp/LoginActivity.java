package com.example.myapplication.TikTokApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    Button btnLogIn;
    EditText username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogIn = (Button) findViewById(R.id.login_btn);
        username = (EditText) findViewById(R.id.editUsername);
    }

    @Override
    protected void onStart() {
        super.onStart();

        btnLogIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AppMainActivity.class);
                intent.putExtra("username", username.getText().toString());
                startActivity(intent);
            }
        });
    }
}