package com.example.appstreaming;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //l'ensemble des permissions qu'on veut
    private static final String[] PERMISSIONS={
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET

    };
    private static final int REQUESTED_PERMISSIONS=12345;
    private static final int PERMISSIONS_COUNT=2;
    //verifier si les permissions sont garenties
    @SuppressLint("NewApi")
    private boolean arePermissionsDenied(){
        for(int i=0;i<PERMISSIONS_COUNT;i++){
            if(checkSelfPermission(PERMISSIONS[i])!= PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }
    // traitement a executer en cas de reponse a une demande de permission
    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(arePermissionsDenied()){
            ((ActivityManager)(this.getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
            recreate();
        }else{
            onResume();
        }
    }
    //lorsqu'on demare l'activite on va verifier si les permissions sont attribuees
    //si non on va demander
    @Override
    protected void onResume() {
        super.onResume();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && arePermissionsDenied()){
            requestPermissions(PERMISSIONS,REQUESTED_PERMISSIONS);//reclame la permission
            return;
        }
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