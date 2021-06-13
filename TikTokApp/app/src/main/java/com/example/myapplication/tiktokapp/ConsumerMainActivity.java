package com.example.myapplication.TikTokApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConsumerMainActivity extends AppCompatActivity {

    Button cancel;
    Button search;
    EditText search_key;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_main);

        cancel = (Button) findViewById(R.id.search_abort_btn);
        search = (Button) findViewById(R.id.search_btn);
        search_key = (EditText) findViewById(R.id.editTextSearchKey);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }

    @Override
    protected void onStart() {
        super.onStart();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AppMainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), null); //todo: add search next screen
                intent.putExtra("username", username);

                ConsumerRunner run = new ConsumerRunner();
                run.execute();

                startActivity(intent);
            }
        });
    }

    private class ConsumerRunner extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            Consumer c = new Consumer(5000, username);

            c.searchVideo(search_key.getText().toString());

            Log.e("DebugInfo: search OK", "null");
            return "ok";
        }
    }
}