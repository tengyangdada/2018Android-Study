package com.example.playaudiotest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MediaPlayer mediaPlayer = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button play =(Button)findViewById(R.id.play);
        Button pause = (Button)findViewById(R.id.pause);
        Button stop = (Button)findViewById(R.id.stop);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else {
            initMediaPlayer();
        }

    }
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                initMediaPlayer();
            }else {
                Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                finish();
            }
            break;
            default:
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play:
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
                break;
            case R.id.pause:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
            case R.id.stop:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.reset();
                    initMediaPlayer();
                }
                break;
                default:
                    break;
        }
    }

    private void initMediaPlayer() {
        try {
            File file=new File(Environment.getExternalStorageDirectory(),"music.mp3");
            Log.i("音乐文件路径", file.getPath());
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    protected void onDestroy(){
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

    }
}
