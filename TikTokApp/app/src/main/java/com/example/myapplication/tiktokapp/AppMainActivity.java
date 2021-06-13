package com.example.myapplication.TikTokApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AppMainActivity extends AppCompatActivity {

    private TextView welcome;
    private Button new_video_publish_btn;
    private Button search_video_btn;
    private Button sign_out_btn;
    private String username;
    private String helloMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        welcome = (TextView) findViewById(R.id.welcome_view);
        new_video_publish_btn = (Button) findViewById(R.id.publish_new_btn);
        search_video_btn = (Button) findViewById(R.id.new_search_btn);
        sign_out_btn = (Button) findViewById(R.id.sign_out_btn);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        helloMsg = welcome.getText().toString();

        welcome.setText(helloMsg+": "+username);
    }

    @Override
    protected void onStart(){
        super.onStart();

        new_video_publish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PublisherMainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        search_video_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConsumerMainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        sign_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}