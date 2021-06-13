package com.example.myapplication.TikTokApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class PublisherMainActivity extends AppCompatActivity {

    Button cancel;
    Button upload;
    EditText file;
    EditText name;
    EditText hashtag;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_main);

        cancel = (Button) findViewById(R.id.publish_abort_btn);
        upload = (Button) findViewById(R.id.upload_btn);
        file = (EditText) findViewById(R.id.editTextVideoPath);
        name = (EditText) findViewById(R.id.editTextVideoName);
        hashtag = (EditText) findViewById(R.id.editTextHashtags);

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

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AppMainActivity.class);
                intent.putExtra("username", username);

                Context context = getApplicationContext();
                CharSequence text = "Video Uploaded Successfully!";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                PublisherRunner run = new PublisherRunner();
                run.execute();
                startActivity(intent);
            }
        });
    }

    private class PublisherRunner extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            ChannelName channelName = new ChannelName(username);
            Publisher p = new Publisher(5000, channelName);

            p.push(file.getText().toString(), name.getText().toString(), hashtag.getText().toString());

            Log.e("DebugInfo: PR OK", "null");
            return "ok";
        }
    }
}