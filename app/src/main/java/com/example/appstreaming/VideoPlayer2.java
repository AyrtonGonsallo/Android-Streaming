package com.example.appstreaming;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoPlayer2 extends AppCompatActivity {

    private VideoView mainVideoView;
    private TextView duration;
    private  TextView current;
    private ListView commentListView;
    private TextView videoCommentaireMessage;
    private SeekBar progress;
    private ImageButton play_pause;
    private Uri videoURI;
    private ProgressBar buffer;
    private boolean isplaying=false;
    private int d=0,c=0;
    private Episods currentSerieEpisod;
    private ImageView full_screen_button;
    private  boolean fullscreen=false;
    private String cur;
    private LinearLayout commentZone;
    CommentModel cModel;
    List<Comments> commentsList=new ArrayList<>();

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        currentSerieEpisod= (Episods) getIntent().getSerializableExtra("episod");
        Log.i("SerieEpisod to play",currentSerieEpisod.getName());
        setContentView(R.layout.activity_video_player2);
        mainVideoView=findViewById(R.id.mainVideoView);
        duration=findViewById(R.id.duration);
        current=findViewById(R.id.current);

        progress=findViewById(R.id.progress);
        full_screen_button=findViewById(R.id.image_full_screen);
        buffer=findViewById(R.id.buffer_progress);
        play_pause =findViewById(R.id.play_pause);
        videoCommentaireMessage=findViewById(R.id.videoCommentaireMessage);
        videoURI=Uri.parse(currentSerieEpisod.getVideopath());
        mainVideoView.setVideoURI(videoURI);
        commentZone =findViewById(R.id.commentZone);
        TextView userText=findViewById(R.id.user);
        String fn="Uid:  "+MyApplication.getInstance().getUtilisateur().getId()+"\nName:  "+MyApplication.getInstance().getUtilisateur().getFirstname()+"\nSirname:  "+MyApplication.getInstance().getUtilisateur().getLastname();
        userText.setText(fn);


        commentListView=findViewById(R.id.commentList);
        cModel=new CommentModel(getApplicationContext(),R.layout.comment,commentsList);
        commentListView.setAdapter(cModel);
        getComments();

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
        int sid=currentSerieEpisod.getSid();
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
                    clees[2]="sid";
                    valeurs[0]=String.valueOf( uid);
                    valeurs[1]=text;
                    valeurs[2]=String.valueOf(sid);
                    //https://github.com/VishnuSivadasVS/Advanced-HttpURLConnection
                    PutData pd=new PutData("https://interface-android-mysql.herokuapp.com/addComment.php","POST",clees,valeurs);
                    if(pd.startPut()){
                        if(pd.onComplete()){
                            String res=pd.getResult();
                            if(res.equals("add serie comment Success")){
                                Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
                                getComments();
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
    public void getComments(){

        String url = "https://interface-android-mysql.herokuapp.com/getComments.php";

        Map<String, String> params = new HashMap();
        params.put("sid", String.valueOf(currentSerieEpisod.getSid()));
        commentsList.clear();
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("comments",response.toString());
                try {
                    JSONArray tableau=response.getJSONArray("comments");
                    for (int i=0;i<tableau.length();i++){
                        JSONObject comment=tableau.getJSONObject(i);
                        Comments com=new Comments();
                        com.setDate(Date.valueOf(comment.getString("date")));
                        com.setCid(comment.getInt("cid"));
                        com.setSid(comment.getInt("sid"));
                        com.setText(comment.getString("text"));
                        com.setUid(comment.getInt("user_id"));

                        commentsList.add(com);
                    }
                    cModel.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
            }
        });

        Volley.newRequestQueue(this).add(jsonRequest);

    }

}