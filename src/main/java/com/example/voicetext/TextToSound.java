package com.example.voicetext;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

public class TextToSound extends AppCompatActivity {
    TextToSpeech tts;
    ImageView voice;
    EditText edit;
    Button bu1;
    String std;
    String[] rs;
    Intent i;
    ImageButton change_tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_sound);

        edit = (EditText)findViewById(R.id.ed1);
        voice = (ImageView)findViewById(R.id.mic);
        bu1 = (Button)findViewById(R.id.bu1);
        change_tts =findViewById(R.id.change_tts);

        //TextToSound 이벤트처리 secondActivity로 이동
        change_tts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                i = new Intent(TextToSound.this,SoundToText.class);
            }
        });

        //음성출력
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            public void onInit(int status) {
                if (status!= TextToSpeech.ERROR){
                    tts.setLanguage(Locale.KOREAN);
                }

            }
        });
        //음성출력 이벤트처리
        voice.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = LOLLIPOP)
            public void onClick(View v) {
                std = edit.getText().toString();
                if(std.length()==0){
                    Toast.makeText(getApplicationContext(),"내용을 입력해주세요.",Toast.LENGTH_SHORT).show();

                }
                else {
                    if (Build.VERSION.SDK_INT >= LOLLIPOP){
                        //ttsGreater21(std);
                    }else{
                       // ttsUnder20(std);
                    }
                }
            }
        });

        //파일 저장 이벤트처리
        bu1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i = new Intent(TextToSound.this,SaveActivity.class);
                i.putExtra("save2",rs);
                startActivity(i);
            }
        });

    }
/*
    //음성출력함수
    protected void onDestroy(){
        super.onDestroy();
        if (tts!=null){
            tts.stop();
            tts.shutdown();
        }
    }

    private void ttsUnder20(String text){
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,"MessageId");
        String test = null;
        tts.speak(test, TextToSpeech.QUEUE_FLUSH,map);
    }

    @RequiresApi(api = LOLLIPOP)
    private void ttsGreater21(String text){
        String utteranceId = this.hashCode()+"";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH,null,utteranceId);
    }
    */
}
