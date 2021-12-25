package com.example.appstreaming;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie_Search extends AppCompatActivity {
    private GridView moviesSearchView;
    private EditText searchMovieText;
    UrlBroker broker;

    private CatalogueModel model;
    private RadioButton radioButtonT,radioButtonK,radioButtonD;
    List<Movies> foundMovies=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
        TextView userText=findViewById(R.id.user);
        String fn="Uid:  "+MyApplication.getInstance().getUtilisateur().getId()+"\nName:  "+MyApplication.getInstance().getUtilisateur().getFirstname()+"\nSirname:  "+MyApplication.getInstance().getUtilisateur().getLastname();
        userText.setText(fn);
        moviesSearchView=findViewById(R.id.moviesSearchView);
        radioButtonD=findViewById(R.id.radioButtonD);
        radioButtonT=findViewById(R.id.radioButtonT);
        radioButtonK=findViewById(R.id.radioButtonK);
        searchMovieText=findViewById(R.id.searchMovieText);
        model=new CatalogueModel(getApplicationContext(),R.layout.movie,foundMovies);
        moviesSearchView.setAdapter(model);
        broker=new UrlBroker();

    }//https://interface-android-mysql.herokuapp.com/findMoviesByDescription_Title_Keyword.php?title=joker
    public void searchMovie(View v){
        String url="https://interface-android-mysql.herokuapp.com/findMoviesByDescription_Title_Keyword.php?";
        String value=String.valueOf(searchMovieText.getText());
        String condition="";
        if(radioButtonT.isChecked()){
            condition="title=";
        }else if(radioButtonD.isChecked()){
            condition="description=";
        }else if(radioButtonK.isChecked()){
            condition="keyword=";
        }
        url+=condition;
        url+=value;
        Log.i("Search url ",url);
        searchMovies(url);

    }

    public void searchMovies(String url){
        foundMovies.clear();model.notifyDataSetChanged();
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        StringRequest sr=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jo;
                try {
                    Log.i("films reponse",response);
                    jo=new JSONObject(response);
                    JSONArray tableau=jo.getJSONArray("data");
                    for (int i=0;i<tableau.length();i++){
                        Movies mi=new Movies();
                        JSONObject film=tableau.getJSONObject(i);
                        mi.setMid(film.getInt("mid"));
                        mi.setName(film.getString("name"));
                        mi.setDescription(film.getString("description"));
                        mi.setRuntime(film.getString("runtime"));
                        mi.setStatus("ONLINE");
                        mi.setImgpath(broker.broke(film.getString("imgpath")));
                        mi.setVideopath(broker.broke(film.getString("videopath")));
                        foundMovies.add(mi);
                    }
                    model.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("films reponse","pas de connexion");
            }
        });
        queue.add(sr);
    }
}