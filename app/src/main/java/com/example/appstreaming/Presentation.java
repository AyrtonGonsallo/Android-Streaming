package com.example.appstreaming;


import android.util.Log;
import android.widget.GridView;
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

public class Presentation extends AppCompatActivity {
    private GridView moviesView;
    private GridView seriesView;
    UrlBroker broker;
    private SeriesModel seriemodel;
    private CatalogueModel model;
    List<Series> allseries=new ArrayList<>();
    List<Movies> allmovies=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);
        //liasion pour films
        moviesView=findViewById(R.id.moviesView);
        model=new CatalogueModel(getApplicationContext(),R.layout.movie,allmovies);
        moviesView.setAdapter(model);
        //liaison pour series
        seriesView=findViewById(R.id.seriesView);
        seriemodel=new SeriesModel(getApplicationContext(),R.layout.movie,allseries);
        seriesView.setAdapter(seriemodel);
        broker=new UrlBroker();
        //recuperer les films et series de la base
        getMoviesData();
        getSeriesData();

    }

    public void getMoviesData(){
        Log.i("films","requete en cours");
        allmovies.clear();model.notifyDataSetChanged();
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://interface-android-mysql.herokuapp.com/getLast4Movies.php";
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
                        allmovies.add(mi);
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

    public void getSeriesData(){
        Log.i("series debut","requete en cours");
        allseries.clear();seriemodel.notifyDataSetChanged();
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://interface-android-mysql.herokuapp.com/getLast4Series.php";
        StringRequest sr=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jo;
                try {
                    Log.i("series reponse",response);
                    jo=new JSONObject(response);
                    JSONArray tableau=jo.getJSONArray("data");
                    for (int i=0;i<tableau.length();i++){
                        Series si=new Series();
                        JSONObject array_of_series=tableau.getJSONObject(i);
                        si.setSid(array_of_series.getInt("sid"));
                        si.setName(array_of_series.getString("name"));
                        si.setDescription(array_of_series.getString("description"));
                        si.setRuntime(array_of_series.getString("runtime"));
                        si.setImgpath(broker.broke(array_of_series.getString("imgpath")));
                        si.setSeasons(array_of_series.getInt("seasons"));
                        si.setEpisods((array_of_series.getInt("episods")));
                        allseries.add(si);
                    }
                    seriemodel.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("series reponse","pas de connexion");
            }
        });
        queue.add(sr);
    }

}
