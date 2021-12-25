package com.example.appstreaming;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.nio.Buffer;

public class VideoPlayer extends AppCompatActivity {
    private VideoView mainVideoView;
    private TextView duration;
    private  TextView current;
    private TextView videoCommentaireMessage;
    private SeekBar progress;
    private ImageButton play_pause;
    private Uri videoURI;
    private ProgressBar buffer;
    private boolean isplaying=false;
    private int d=0,c=0;
    Movies currentMovie;
    private ImageView full_screen_button;
    private  boolean fullscreen=false;
    private String cur;
    private LinearLayout commentZone;


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
        full_screen_button=findViewById(R.id.image_full_screen);
        buffer=findViewById(R.id.buffer_progress);
        play_pause =findViewById(R.id.play_pause);
        videoCommentaireMessage=findViewById(R.id.videoCommentaireMessage);
        videoURI=Uri.parse(currentMovie.getVideopath());
        mainVideoView.setVideoURI(videoURI);
        commentZone =findViewById(R.id.commentZone);
        TextView userText=findViewById(R.id.user);
        String fn="Uid:  "+MyApplication.getInstance().getUtilisateur().getId()+"\nName:  "+MyApplication.getInstance().getUtilisateur().getFirstname()+"\nSirname:  "+MyApplication.getInstance().getUtilisateur().getLastname();
        userText.setText(fn);
        if(currentMovie.getStatus().equals("OFFLINE")){
                commentZone.setVisibility(View.GONE);


        }
        mainVideoView.requestFocus();
        if(savedInstanceState!=null){
            c=savedInstanceState.getInt("time");
            d=savedInstanceState.getInt("duration");
            progress.setMax(d);
            progress.setProgress(c);
            mainVideoView.seekTo(c);
            current.setText(String.format("%02d:%02d",c/60000,(c/1000)%60));
            savedInstanceState.remove("time");
            savedInstanceState.remove("duration");
        }






        full_screen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fullscreen==false){
                    fullscreen=true;
                    full_screen_button.setImageResource(R.drawable.fullscreen_in);//vers l'interieur
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                else{
                    fullscreen=false;
                    full_screen_button.setImageResource(R.drawable.fullscreenout);//vers l'exterieur
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });

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
                                    cur=String.format("%02d:%02d",c/60000,(c/1000)%60);
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
                cur=String.format("%02d:%02d",c/60000,(c/1000)%60);
                current.setText(cur);
            }
        });


    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("time", c);
        savedInstanceState.putInt("duration", d);

    }
    public void sendComment(View v){
        //a changer
        String text;
        text=String.valueOf(videoCommentaireMessage.getText());
        User u=MyApplication.getInstance().getUtilisateur();
        int mid=currentMovie.getMid();
        int uid=u.getId();
        if( !(text.equals("")) ){
            Handler handler=new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String[] clees=new String[3];
                    String[] valeurs=new String[3];
                    clees[0]="uid";
                    clees[1]="text";
                    clees[2]="mid";
                    valeurs[0]=String.valueOf( uid);
                    valeurs[1]=text;
                    valeurs[2]=String.valueOf(mid);
                    //https://github.com/VishnuSivadasVS/Advanced-HttpURLConnection
                    PutData pd=new PutData("https://interface-android-mysql.herokuapp.com/addComment.php","POST",clees,valeurs);
                    if(pd.startPut()){
                        if(pd.onComplete()){
                            String res=pd.getResult();
                            if(res.equals("add movie comment Success")){
                                Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"Comment required",Toast.LENGTH_SHORT).show();
        }
    }

}