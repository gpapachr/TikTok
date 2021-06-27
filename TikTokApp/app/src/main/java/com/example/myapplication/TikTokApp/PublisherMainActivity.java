package com.example.myapplication.TikTokApp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.*;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class PublisherMainActivity extends AppCompatActivity {

    private static int VIDEO_REQUEST = 101;
    private Uri videoUri = null;

    Button cancel;
    Button upload;
    Button capture;

    EditText file;
    EditText name;
    EditText hashtag;

    String username;

    VideoView videoView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_main);

        cancel = (Button) findViewById(R.id.publish_abort_btn);
        upload = (Button) findViewById(R.id.upload_btn);
        capture = (Button) findViewById(R.id.capture_video_btn);

        file = (EditText) findViewById(R.id.editTextVideoPath);
        name = (EditText) findViewById(R.id.editTextVideoName);
        hashtag = (EditText) findViewById(R.id.editTextHashtags);

        videoView = (VideoView) findViewById(R.id.videoView);

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

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent, VIDEO_REQUEST);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == VIDEO_REQUEST){
            videoUri = data.getData();
            videoView.setVideoURI(videoUri);
            videoView.start();
        }
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