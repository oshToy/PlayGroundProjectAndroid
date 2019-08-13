package com.example.firstapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private EditText inputText;
    private final OkHttpClient client = new OkHttpClient();
    private Bitmap bitmap;
    private String urlString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Handler handler = new Handler();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    inputText = findViewById(R.id.input);
    findViewById(R.id.button_download).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            urlString =inputText.getText().toString();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    Request request = new Request.Builder()
                            .url(urlString)
                            .build();
                        Response response = client.newCall(request).execute();
                        if (response.body() != null) {
                            InputStream inputStream = response.body().byteStream();
                            bitmap = BitmapFactory.decodeStream(inputStream);
                            handler.post(new Runnable() {
                                             @Override
                                             public void run() {
                                                 ImageView imgview = (ImageView)findViewById(R.id.image_view);
                                                 imgview.setImageBitmap(bitmap);
                                             }
                                         }
                            );
                        }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }

        });

    }


}
