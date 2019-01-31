package com.example.android.gifconverter;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public Button uploadButton;
    public ProgressBar progressBar;
    public int SELECT_VIDEO = 2;
    public ImageView image1;
    public DownloadManager downloadManager;
    public Button downloadBtn;
    public String gifUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        MediaManager.init(this);
        image1 = findViewById(R.id.img1);
        uploadButton = findViewById(R.id.uploadBtn);
        downloadBtn = findViewById(R.id.download_btn);
        downloadBtn.setVisibility(View.INVISIBLE);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickVideoFromGallery();
            }

            private void pickVideoFromGallery(){
                Intent GalleryIntent = new Intent();
                GalleryIntent.setType("video/*");
                GalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(GalleryIntent, "select video"), SELECT_VIDEO);
            }


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data){

        if (requestCode == SELECT_VIDEO && resultCode == RESULT_OK){
            Uri selectedVideo = data.getData();
            MediaManager.get()
                    .upload(selectedVideo)
                    .unsigned("preset_name")
                    .option("resource_type", "video")
                    .callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {

                            progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Upload has started...", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {

                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {

                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {

                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {

                        }
                    });
        }

    }
}
