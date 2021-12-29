package com.example.appstreaming;

import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeriesSeasons extends AppCompatActivity {
    Series currentSerie;
    GridView seasonsView;
    SeasonsModel sm;
    UrlBroker broker=new UrlBroker();
    List<Seasons>allSeasons=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("get seasons","get seasons ");
        setContentView(R.layout.activity_series_seasons);
        TextView userText=findViewById(R.id.user);
        String fn="Uid:  "+MyApplication.getInstance().getUtilisateur().getId()+"\nName:  "+MyApplication.getInstance().getUtilisateur().getFirstname()+"\nSirname:  "+MyApplication.getInstance().getUtilisateur().getLastname();
        userText.setText(fn);

        seasonsView=findViewById(R.id.seasonsView);
        sm=new SeasonsModel(getApplicationContext(),R.layout.season,allSeasons);
        seasonsView.setAdapter(sm);
        currentSerie= (Series) getIntent().getSerializableExtra("serie");
        getSeasons();

    }

    public void getSeasons(){

        String url = "https://interface-android-mysql.herokuapp.com/getSeasons.php";

        Map<String, String> params = new HashMap();
        params.put("sid", String.valueOf(currentSerie.getSid()));
        allSeasons.clear();
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("seasons",response.toString());
                try {
                    JSONArray tableau=response.getJSONArray("seasons");
                    for (int i=0;i<tableau.length();i++){
                        JSONObject seas=tableau.getJSONObject(i);
                        Seasons season=new Seasons();
                        season.setId(seas.getInt("id"));
                        season.setDescription(seas.getString("description"));
                        season.setName(seas.getString("name"));
                        season.setSid(seas.getInt("sid"));
                        season.setImgpath(broker.broke(seas.getString("imgpath")));
                        season.setNep(seas.getInt("NEP"));
                        allSeasons.add(season);
                    }
                    sm.notifyDataSetChanged();

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

    }
}