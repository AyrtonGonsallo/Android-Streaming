package com.example.appstreaming;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
                    //Write data with POST and GET methods - PutData.class
                    PutData pd=new PutData("https://interface-android-mysql.herokuapp.com/login.php","POST",clees,valeurs);
                    if(pd.startPut()){
                        if(pd.onComplete()){
                            String res=pd.getResult();
                            if(res.equals("Login Success")){
                                Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
                                //sauvegarder l'objet user dans une variable de session user de la classe myApplication declaree a laligne 8 du manifest
                                getUser(valeurs[0],valeurs[1]);
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                startActivity(new Intent(getApplicationContext(),Offline.class));
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
    public User getUser(String e,String p){
        User u=new User();
        String url = "https://interface-android-mysql.herokuapp.com/getUser.php";

        Map<String, String> params = new HashMap();
        params.put("password", p);
        params.put("email", e);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject user=response.getJSONObject("user");

                    u.setFirstname(user.getString("firstname"));
                    u.setId(user.getInt("id"));
                    u.setLastname(user.getString("lastname"));
                    Log.i("utilisateur",u.getFirstname()+" "+u.getLastname());
                    //sauvegarder l'utilisateur
                    MyApplication.getInstance().setUtilisateur(u);

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
        return u;
    }
}
