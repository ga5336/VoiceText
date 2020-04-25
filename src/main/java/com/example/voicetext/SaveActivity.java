package com.example.voicetext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class SaveActivity extends AppCompatActivity {
    EditText save_name;
    Button save;
    String rs1, rs2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        save_name = findViewById(R.id.save_name);
        save = findViewById(R.id.save);

        boolean sec_act = false;

        //저장할 내용 받아오기
        Intent i = new Intent();
        i.putExtra("save1",rs1);
        if(rs1 != null){
            sec_act =true;
            rs1 = i.getExtras().getString("save1");
        }else{
            i.putExtra("save2",rs2);
            rs2=i.getExtras().getString("save2");
        }
        //파일저장 이벤트처리
        final boolean finalSac_act = sec_act;
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if(finalSac_act==true){
                        file_save(rs1);
                    }else{
                        file_save(rs2);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        });

        if (sec_act==true){
            i = new Intent(SaveActivity.this,SoundToText.class);
        }else{
            i = new Intent(SaveActivity.this,TextToSound.class);
        }
        finish();
    }

    public void file_save(String rs) throws IOException{
        //user가 쓴 파일명 저장
        String name1 = save_name.getText().toString();
        String name = name1+".txt";

        if (name == null || name.equals("")) {
            Toast.makeText(getApplicationContext(),"저장할 text의 이름을 입력해주세요.",Toast.LENGTH_SHORT).show();
            //내용 없을때 예외처리
        }else{
            File file = new File(name);
            FileWriter fw = null;
            try{
                //파일저장
                fw = new FileWriter(file);
                fw.write(rs);
                Toast.makeText(getApplicationContext(),"음성이 file"+name+".txt로 저장되었습니다.",Toast.LENGTH_SHORT).show();
            }catch (IOException e){//예외처리
                e.printStackTrace();
            }
            if (fw != null){
                try {
                    fw.close();//파일닫기
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
