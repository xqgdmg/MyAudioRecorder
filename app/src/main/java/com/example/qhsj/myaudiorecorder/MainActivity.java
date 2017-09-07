package com.example.qhsj.myaudiorecorder;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qhsj.myaudiorecorder.utils.MyAudioRecorder;

import java.io.File;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private TextView tvStarrt;
    private TextView tvStop;
    private MyAudioRecorder myAudioRecorder;
    boolean first = true;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initRecorder();
        initListener();
    }

    private void initRecorder() {
        myAudioRecorder = new MyAudioRecorder(new MyAudioRecorder.SoundLevelListener() {
            @Override
            public void getSoundLevel(int level) {
//                sv.addSpectrum(level*density*3);
                Log.e("chris","level=" + level);
            }
        });
        myAudioRecorder.setMaxLevel(16);
    }

    private void initListener() {
        tvStarrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioRecorder.isShowLevel = true;
                Toast.makeText(MainActivity.this,"开始录音!",Toast.LENGTH_SHORT).show();
                startRecord(); // 录音
            }
        });

        tvStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"停止录音!",Toast.LENGTH_SHORT).show();
                myAudioRecorder.stop();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });
    }

    private void startRecord() {
         // 创建文件夹
        String dirsPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/chris/Record/";
        if (!new File(dirsPath).exists()){
            new File(dirsPath).mkdirs();
        }

        path = dirsPath + UUID.randomUUID() + ".wav";
        Log.e("path1=",path);
        myAudioRecorder.start(path);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void initView() {
        tvStarrt = (TextView) findViewById(R.id.tvStarrt);
        tvStop = (TextView) findViewById(R.id.tvStop);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myAudioRecorder.stop();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


}
