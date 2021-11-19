package com.example.appstreaming;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class loginform extends AppCompatActivity {
    EditText mail,passwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginform);
        mail=findViewById(R.id.Username_email_value);
        passwd=findViewById(R.id.Password_value);
    }
    public void validation(View v){
        //a changer
        String m,p;
        m=String.valueOf(mail.getText());
        p=String.valueOf(passwd.getText());
        if( !(m.equals(""))  &&  !(p.equals("")) ){
            Handler handler=new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String[] clees=new String[2];
                    String[] valeurs=new String[2];
                    clees[0]="email";
                    clees[1]="password";
                    valeurs[0]=m;
                    valeurs[1]=p;
                    //https://github.com/VishnuSivadasVS/Advanced-HttpURLConnection
                    PutData pd=new PutData("https://interface-android-mysql.herokuapp.com/login.php","POST",clees,valeurs);
                    if(pd.startPut()){
                        if(pd.onComplete()){
                            String res=pd.getResult();
                            if(res.equals("Login Success")){
                                Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),VideoPlayer.class));
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