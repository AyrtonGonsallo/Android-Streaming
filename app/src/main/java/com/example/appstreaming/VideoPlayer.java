package com.example.appstreaming;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.nio.Buffer;

public class VideoPlayer extends AppCompatActivity {
    private VideoView mainVideoView;
    private TextView duration;
    private  TextView current;
    private SeekBar progress;
    private ImageButton play_pause;
    private Uri videoURI;
    private ProgressBar buffer;
    private boolean isplaying=false;
    private int d=0,c=0;
    Movies currentMovie;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentMovie= (Movies) getIntent().getSerializableExtra("movie");
        Log.i("movie to play",currentMovie.getName());
        setContentView(R.layout.activity_video_player);
        mainVideoView=findViewById(R.id.mainVideoView);
        duration=findViewById(R.id.duration);
        current=findViewById(R.id.current);
        progress=findViewById(R.id.progress);

        buffer=findViewById(R.id.buffer_progress);
        play_pause =findViewById(R.id.play_pause);
        videoURI=Uri.parse(currentMovie.getVideopath());
        mainVideoView.setVideoURI(videoURI);
        mainVideoView.requestFocus();
        mainVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int i, int i1) {
                if(i==MediaPlayer.MEDIA_INFO_BUFFERING_START){
                    buffer.setVisibility(View.VISIBLE);
                    Log.i("buffer","buffering en cours");
                    return true;
                }else if(i==MediaPlayer.MEDIA_INFO_BUFFERING_END){
                    buffer.setVisibility(View.INVISIBLE);
                    return true;
                }

                return false;
            }
        });

        mainVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                d=mediaPlayer.getDuration();
                progress.setMax(d);
                String dur=String.format("%02d:%02d",d/60000,(d/1000)%60);
                duration.setText(dur);
                new Thread(){

                    @Override
                    public void run() {
                        super.run();
                        while(c<mainVideoView.getDuration()){
                            if(isplaying)c+=1000;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress.setProgress(c);
                                    String cur=String.format("%02d:%02d",c/60000,(c/1000)%60);
                                    current.setText(cur);

                                }
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        });
        mainVideoView.start();
        isplaying=true;
        //@android:drawable/ic_media_play
        play_pause.setImageResource(android.R.drawable.ic_media_pause);
        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isplaying){
                    mainVideoView.pause();
                    play_pause.setImageResource(android.R.drawable.ic_media_play);
                    isplaying=false;
                }
                else{
                    mainVideoView.start();
                    play_pause.setImageResource(android.R.drawable.ic_media_pause);
                    isplaying=true;

                }
            }
        });
        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int videoProgress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                videoProgress=i;
                c=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mainVideoView.seekTo(videoProgress);
            }
        });


    }



}