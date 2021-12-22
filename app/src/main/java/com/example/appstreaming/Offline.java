package com.example.appstreaming;

import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Offline extends AppCompatActivity {
    private List<String> videoFilesList=new ArrayList<>();;
    private GridView offLineView;
    List<Movies> allmovies=new ArrayList<>();
    private CatalogueModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        offLineView=findViewById(R.id.offLineView);
        model=new CatalogueModel(getApplicationContext(),R.layout.movie,allmovies);
        offLineView.setAdapter(model);
    }

    private void addMusicFilesFrom(String dirpath){
        final File videoDir=new File(dirpath);
        //si le repertoire n'existe pas on le cree
        if(!videoDir.exists()){
            videoDir.mkdir();
            return;
        }
        final File[]files=videoDir.listFiles();
        int i=0;
        for(File f:files){
            final String path=f.getAbsolutePath();
            if(path.endsWith(".mp4")){
                i++;
                videoFilesList.add(path);
                Log.i("video stockee",path);
            }
            if(i==5)break;
        }
    }

    private void  fillmusicList(){
        videoFilesList.clear();
        allmovies.clear();
        model.notifyDataSetChanged();
        addMusicFilesFrom(String.valueOf(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
        )));
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillmusicList();
        for (int i=0;i<5;i++){
            String s=videoFilesList.get(i);
            Movies m=new Movies();
            m.setName(s.substring(s.lastIndexOf('/')+1));
            m.setVideopath(s);
            m.setStatus("OFFLINE");
            m.setImgpath("https://png.pngtree.com/element_our/20190602/ourlarge/pngtree-black-and-white-video-play-icon-image_1414976.jpg");
            allmovies.add(m);
        }
        model.notifyDataSetChanged();

    }
    public void goOnline(View v){
        startActivity(new Intent(getApplicationContext(),Presentation.class));
    }
}