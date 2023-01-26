package com.example.iqtest;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView tvMondai,tvResult;
    Button btAnswer,btNext,btPrevious,btRestart;
    ImageView ivQues;
    static final int REQUEST_CODE = 12345;
    int i=0,count=0,total=0;
    int imageResource[] = {
            R.drawable.ques1,
            R.drawable.ques2,
            R.drawable.ques3,
            R.drawable.ques4,
            R.drawable.ques5,
            R.drawable.ques6,
            R.drawable.ques7,
            R.drawable.ques8,
            R.drawable.ques9,
            R.drawable.ques10
    };
    String Answer[]={
            "電話",
            "花火",
            "茶道",
            "日本",
            "安心",
            "神社",
            "学生",
            "恋人",
            "すみません",
            "辞書"
    };

    public void setIvQues(ImageView ivQues) {
        this.ivQues = ivQues;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMondai = findViewById(R.id.tvMondai);
        tvResult = findViewById(R.id.tvResult);
        btAnswer = findViewById(R.id.btAnswer);
        btNext = findViewById(R.id.btNext);
        btRestart = findViewById(R.id.btRestart);
        btRestart.setEnabled(false);
        btPrevious = findViewById(R.id.btPrevious);
        ivQues= findViewById(R.id.ivQues);
        tvMondai.setText("第 " + (i + 1) + "問：");
        btAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                //認識する言語を指定（この場合は日本語）
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.JAPANESE.toString());
                //認識する候補数の指定
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
                //音声認識時に表示する案内を設定
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "話してください");
                //音声認識を開始
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i<9) {
                    i++;
                    tvMondai.setText("第 " + (i+1) + " 問:");
                    tvResult.setText("");
                    ivQues.setImageResource(imageResource[i]);
                }else if(i>=9){
                    tvResult.setText("終了しました");
                    total=count*15;
                    if(total<=115){
                        tvMondai.setText("正解率："+count+"/10\n貴方のIQは"+total+"です。\n貴方は凡人です");
                    }else if(total >115 && total <= 130){
                        tvMondai.setText("正解率："+count+"/10\n貴方のIQは"+total+"です。\n貴方は賢人です");
                    }else if(total > 130 && total <= 145){
                        tvMondai.setText("正解率："+count+"/10\n貴方のIQは"+total+"です。\n貴方は秀才です");
                    }else if(total >145){
                        tvMondai.setText("正解率："+count+"/10\n貴方のIQは"+total+"です。\n貴方は天才です");
                    }
                    ivQues.setVisibility(View.INVISIBLE);
                    btAnswer.setEnabled(false);
                    btNext.setEnabled(false);
                    btPrevious.setEnabled(false);
                    btRestart.setEnabled(true);
                }
            }
        });
        btPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i>0) {
                    i--;
                    tvMondai.setText("第 " + (i+1) + " 問:");
                    tvResult.setText("");
                    ivQues.setImageResource(imageResource[i]);
                }else{
                    tvResult.setText("前の問題はございません");
                }
            }
        });
        btRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count=0;
                i=0;
                tvMondai.setText("第 " + (i+1) + " 問:");
                tvResult.setText("");
                ivQues.setImageResource(imageResource[i]);
                ivQues.setVisibility(View.VISIBLE);
                btAnswer.setEnabled(true);
                btNext.setEnabled(true);
                btPrevious.setEnabled(true);
                btRestart.setEnabled(false);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            ArrayList<String> kk = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if(kk.size() > 0){
                tvResult.setText(kk.get(0));
                if(kk.get(0).equals(Answer[i])){
                    Toast toast =
                    Toast.makeText(this,"\uD83D\uDE0A      正解     ✔", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.getView().setBackgroundColor(Color.parseColor("#00FF00"));
                    toast.show();
                    count ++;
                }else{
                    Toast toast = Toast.makeText(this,"\uD83D\uDE24     不正解     ✘", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.getView().setBackgroundColor(Color.parseColor("#FF0000"));
                    toast.show() ;               }
            }else{
                tvResult.setText("音声認識失敗しました");
            }
        }
    }

}