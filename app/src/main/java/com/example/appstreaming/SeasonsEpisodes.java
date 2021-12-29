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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeasonsEpisodes extends AppCompatActivity {
    GridView EpisodsView;
    EpisodsModel em;
    Seasons currentSeason;
    UrlBroker broker=new UrlBroker();
    List<Episods> allEpisods=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasons_episodes);
        TextView userText=findViewById(R.id.user);
        String fn="Uid:  "+MyApplication.getInstance().getUtilisateur().getId()+"\nName:  "+MyApplication.getInstance().getUtilisateur().getFirstname()+"\nSirname:  "+MyApplication.getInstance().getUtilisateur().getLastname();
        userText.setText(fn);

        EpisodsView=findViewById(R.id.EpisodsView);
        em=new EpisodsModel(getApplicationContext(),R.layout.episod,allEpisods);
        EpisodsView.setAdapter(em);
        currentSeason= (Seasons) getIntent().getSerializableExtra("season");
        getEpisods();
    }

    public void getEpisods(){

        String url = "https://interface-android-mysql.herokuapp.com/getEpisods.php";

        Map<String, String> params = new HashMap();
        params.put("sid", String.valueOf(currentSeason.getSid()));
        allEpisods.clear();
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("episod",response.toString());
                try {
                    JSONArray tableau=response.getJSONArray("episods");
                    for (int i=0;i<tableau.length();i++){
                        JSONObject epis=tableau.getJSONObject(i);
                        Episods ep=new Episods();
                        ep.setId(epis.getInt("id"));
                        ep.setDescription(epis.getString("description"));
                        ep.setImgpath(currentSeason.getImgpath());
                        ep.setName(epis.getString("name"));
                        ep.setRuntime(epis.getInt("runtime"));
                        ep.setSeason(epis.getInt("season"));
                        ep.setSid(epis.getInt("sid"));
                        ep.setStatus("ONLINE");
                        ep.setVideopath(broker.broke(epis.getString("videopath")));
                        allEpisods.add(ep);
                    }
                    em.notifyDataSetChanged();

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