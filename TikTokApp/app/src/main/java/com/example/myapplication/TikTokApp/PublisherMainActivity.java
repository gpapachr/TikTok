package com.example.myapplication.TikTokApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.concurrent.Executor;

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
                Log.e("aaaaa", "Try Activity For result");
                startActivityForResult(intent, VIDEO_REQUEST);
                Log.e("aaaaa", "Start Activity For result");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == VIDEO_REQUEST) {
            videoUri = data.getData();
            videoView.setVideoURI(videoUri);
            videoView.start();
        }
    }

    private class PublisherRunner extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
        Cursor cursor = getContentResolver().query(videoUri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        String path = cursor.getString(idx);
        Log.e("aaaaa", path);
        Publisher p = new Publisher(5000, new ChannelName(username));
        p.push(path, name.toString(), hashtag.toString());

        return "ok";
        }
    }
}