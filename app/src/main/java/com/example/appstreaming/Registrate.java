package com.example.appstreaming;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Registrate extends AppCompatActivity {
    private EditText RegistartionFNV,RegistrationLNV,RegistrationPV,RegistrationDNV,Registartionemail_value,RegistartionPassword_value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrate);
        RegistartionFNV=findViewById(R.id.RegistartionFNV);
        RegistrationPV=findViewById(R.id.RegistrationPV);
        RegistrationLNV=findViewById(R.id.RegistrationLNV);
        RegistrationDNV=findViewById(R.id.RegistrationDNV);
        Registartionemail_value=findViewById(R.id.Registartionemail_value);
        RegistartionPassword_value=findViewById(R.id.RegistartionPassword_value);
    }
    public void register(View v){

        String f,l,ph,d,pass,email;
        f=String.valueOf(RegistartionFNV.getText());
        l=String.valueOf(RegistrationLNV.getText());
        ph=String.valueOf(RegistrationPV.getText());
        d=String.valueOf(RegistrationDNV.getText());
        pass=String.valueOf(RegistartionPassword_value.getText());
        email=String.valueOf(Registartionemail_value.getText());
        if( !(f.equals(""))  &&  !(l.equals("")) &&  !(ph.equals("")) &&  !(d.equals("")) &&  !(pass.equals("")) &&  !(email.equals("")) ){
            Handler handler=new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String[] clees=new String[6];
                    String[] valeurs=new String[6];
                    clees[0]="firstname";
                    clees[1]="lastname";
                    clees[2]="email";
                    clees[3]="DOB";
                    clees[4]="phone";
                    clees[5]="password";
                    valeurs[0]=f;
                    valeurs[1]=l;
                    valeurs[2]=email;
                    valeurs[3]=d;
                    valeurs[4]=ph;
                    valeurs[5]=pass;
                    //https://github.com/VishnuSivadasVS/Advanced-HttpURLConnection
                    PutData pd=new PutData("https://interface-android-mysql.herokuapp.com/signup.php","POST",clees,valeurs);
                    if(pd.startPut()){
                        if(pd.onComplete()){
                            String res=pd.getResult();
                            if(res.equals("Sign Up Success")){
                                Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),loginform.class));
                                finish();
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
            Toast.makeText(getApplicationContext(),"All fields required",Toast.LENGTH_SHORT).show();
        }
    }
}