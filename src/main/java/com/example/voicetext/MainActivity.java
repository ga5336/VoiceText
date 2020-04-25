package com.example.voicetext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
    private static final int RESULT_SPEECH = 1; //REQUEST_CODE로 쓰임
    final int PERMISSION = 1;
    ImageView im1, im2;
    private Intent i,b;
    String[] rs;
    SpeechRecognizer mRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //접근확인
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},5);
        }
        if(Build.VERSION.SDK_INT>=23){
            //퍼미션 체크
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.INTERNET,Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }

        //이벤트처리 SoundToText 이동
        im1 = (ImageView) findViewById(R.id.unnamed);
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MainActivity.this, SoundToText.class);
                startActivity(i);
                finish();
            }
        });

        //이벤트처리 TextToSound 이동
        im2 = (ImageView) findViewById(R.id.speaker);
        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = new Intent(MainActivity.this, TextToSound.class);
                startActivity(b);
                finish();
            }
        });
    }
}
