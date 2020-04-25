package com.example.voicetext;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

public class SoundToText extends AppCompatActivity {
    private static final int RESULT_SPEECH = 1; //REQUEST_CODE로 쓰임
    private TextView tv;
    EditText et;
    private Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    ImageView mic;
    ArrayList<String> mResult;
    String[] rs;
    String  rs1;
    Button bu1, get_voice;
    TextToSpeech tts;
    private ImageButton change_stt;
    SpeechRecognizer mRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_to_text);

        get_voice = findViewById(R.id.get_voice);

        //레이아웃의 컴포넌트를 가져옴
        mic = (ImageView) findViewById(R.id.mic);
        tv = (TextView) findViewById(R.id.tv);
        change_stt = findViewById(R.id.change_stt);
        tv.setText(rs1);

        Toast.makeText(SoundToText.this, "Start speak", Toast.LENGTH_SHORT).show();
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "말해주세요");
        Toast.makeText(SoundToText.this, "Start Speak", Toast.LENGTH_SHORT).show();
        //음성인식클래스 실행
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(SoundToText.this);
        mRecognizer.setRecognitionListener(listener);
        mRecognizer.startListening(i);

        try {
            //예외처리
            startActivityForResult(i, RESULT_SPEECH);
        } catch (ActivityNotFoundException e) {
            //예외처리
            Toast.makeText(getApplicationContext(), "Speech To Text를 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
            e.getStackTrace();
        }

        //음성출력
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            public void onInit(int status) {
                if (status!= TextToSpeech.ERROR){
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });
        //음성출력 이벤트처리
        mic.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = LOLLIPOP)
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>= LOLLIPOP){
                    //ttsGreater21(rs);
                }else{
                    //ttsUnder20(rs);
                }
            }
        });

        bu1 = (Button)findViewById(R.id.bu1);//파일저장 이벤트처리 saveActivity로 이동
        bu1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                i=new Intent(SoundToText.this,SaveActivity.class);
                i.putExtra("save1",rs);
                startActivity(i);
            }
        });


        //버튼에 대한 리스너 등록 부분
        get_voice.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Toast.makeText(SoundToText.this, "Start speak", Toast.LENGTH_SHORT).show();
                i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); //인텐트 생성
                i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());//호출한 패키지
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR"); //인식할 언어를 설정한다
                i.putExtra(RecognizerIntent.EXTRA_PROMPT, "말해주세요"); //유저에게 보여줄 문자
                Toast.makeText(SoundToText.this, "Start Speak", Toast.LENGTH_SHORT).show();
                //음성인식클래스 실행
                mRecognizer = SpeechRecognizer.createSpeechRecognizer(SoundToText.this);
                mRecognizer.setRecognitionListener(listener);
                mRecognizer.startListening(i);

            try {
                //예외처리
                startActivityForResult(i, RESULT_SPEECH);

            } catch (ActivityNotFoundException e) {
                //예외처리
                Toast.makeText(getApplicationContext(), "Speech To Text를 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
                e.getStackTrace();
            }
        }
    });

        //음성출력 이벤트처리 thridActivity로 이동
        change_stt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                i=new Intent(SoundToText.this,TextToSound.class);
            }
       });
    }

    /*
    //음성출력 함수
    protected void onDestroy(){
        super.onDestroy();
        if(tts!=null){
            tts.stop();
            tts.shutdown();
        }
    }
    //음성출력 함수
    private void ttsUnder20(String[] text){
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,"MessageId");
        tts.speak(String.valueOf(text), TextToSpeech.QUEUE_FLUSH,map);
    }

    @RequiresApi(api = LOLLIPOP)
    private void ttsGreater21(String[] text){
        String utteranceId = this.hashCode()+"";
        tts.speak(String.valueOf(text),TextToSpeech.QUEUE_FLUSH,null,utteranceId);
    }
    */

    //음성인식 클래스
    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            //사용자가 말하기 시작할 준비가 되면 호출
        }

        @Override
        public void onBeginningOfSpeech() {
            //사용자가 말하기 시작했을때 호출
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            //입력받는 소리의 크기를 알려줌
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            //사용자가 말을 시작하고 인식이 된 단어를 buffer에 담음
        }

        @Override
        public void onEndOfSpeech() {
            //사용자가 말하기를 중지하면 호출
        }

        @Override
        public void onError(int error) {//네트워크 또는 인식 오류가 발생했을 때 호출
            String message;

            switch (error){
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류임";
                    break;
            }

            Toast.makeText(getApplicationContext(), "에러가 발생하였습니다. : " + message,Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onResults(Bundle results) { //인식 결과가 준비되면 호출
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mResult = results.getStringArrayList(key);
            rs = new String[mResult.size()];
            mResult.toArray(rs);
            tv.setText(""+rs);
            mRecognizer.startListening(i);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            //부분 인식 결과를 사용할 수 있을 때 호출
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            //향후 이벤트를 추가하기 위해 예약
        }
    };



}
