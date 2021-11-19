package com.example.appstreaming;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class loginform extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginform);
    }
    public void validation(View v){
        //a changer
        startActivity(new Intent(this,VideoPlayer.class));
    }
}