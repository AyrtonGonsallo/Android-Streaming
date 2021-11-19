package com.example.appstreaming;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void login(View v){
        startActivity(new Intent(this,loginform.class));
    }
    public void registrate(View v){
        startActivity(new Intent(this,Registrate.class));
    }

}




/*
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/AyrtonGonsallo/Android-Streaming.git
git push -u origin main
 */