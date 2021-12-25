package com.example.appstreaming;

import android.app.Application;
import android.util.Log;

public final class MyApplication {

    private static MyApplication mInstance= null;

    private User utilisateur;

    public User getUtilisateur() {
        //Log.i("My application","get utilisateur par une activite");
        return this.utilisateur;
    }

    public void setUtilisateur(User u) {
        //Log.i("My application","set utilisateur: "+u.toString());
        this.utilisateur.setFirstname(u.getFirstname());
        this.utilisateur.setLastname(u.getLastname());
        this.utilisateur.setId(u.getId());
    }

    private MyApplication(){}

    public static synchronized MyApplication getInstance() {
        if(null == mInstance){
            //Log.i("MyApplication","instantiation");
            mInstance = new MyApplication();
            mInstance.utilisateur=new User();
        }
        //Log.i("MyApplication","demande par une activite");
        return mInstance;
    }
}