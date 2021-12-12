package com.example.appstreaming;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Presentation extends AppCompatActivity {
    private GridView moviesView;
    private int clickedMovie;
    private CatalogueModel model;
    List<Movies> allmovies=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);
        moviesView=findViewById(R.id.moviesView);
        model=new CatalogueModel(getApplicationContext(),R.layout.movie,allmovies);
        moviesView.setAdapter(model);
        //int mid, String name, String genre, String rdate, String runtime, String description, String keywords_en, String imgpath, String videopath
        Movies m1=new Movies(2,"joker","crime/drame","2019","122","Dans les années 1980, à Gotham City, Arthur Fleck, un comédien de stand-up raté est agressé alors qu'il ère dans les rues de la ville déguisé en clown. Méprisé de tous et bafoué, il bascule peu à peu dans la folie pour devenir le Joker, un dangereux tueur psychotique.","mental illness, madness, joaquin phoenix","https://ayr-streaming.herokuapp.com/image-uploads/joker.jpg","https://ayr-streaming.herokuapp.com/video-uploads/JOKER%20-%20Final%20Trailer%20-%20Now%20Playing%20In%20Theaters.mp4");
        Movies m2=new Movies(5,"legendary assassin","action/arts martiaux","2008","100","Bo (Wu Jing) débarque sur une petite île près de Hong-Kong pour éxécuter un chef mafieux mais il s'y retrouve coincé à cause d'un typhon. Il rencontre par la suite une agent de police à qui il cache sa véritable identité qui l'aidera à se loger. S'engagera dès lors une chasse à l'homme impitoyable confrontant la pègre locale à notre tueur à gages, sa nouvelle amie et ses collègues.","kung-fu, kung fu, assassin, fight scenes, martial .","https://ayr-streaming.herokuapp.com/image-uploads/legendary%20assassin.jpg","https://ayr-streaming.herokuapp.com/video-uploads/Legendary%20Assassin%20-%20Trailer.mp4");
        Movies m3=new Movies(2,"joker","crime/drame","2019","122","Dans les années 1980, à Gotham City, Arthur Fleck, un comédien de stand-up raté est agressé alors qu'il ère dans les rues de la ville déguisé en clown. Méprisé de tous et bafoué, il bascule peu à peu dans la folie pour devenir le Joker, un dangereux tueur psychotique.","mental illness, madness, joaquin phoenix","https://ayr-streaming.herokuapp.com/image-uploads/joker.jpg","https://ayr-streaming.herokuapp.com/video-uploads/JOKER%20-%20Final%20Trailer%20-%20Now%20Playing%20In%20Theaters.mp4");
        Movies m4=new Movies(5,"legendary assassin","action/arts martiaux","2008","100","Bo (Wu Jing) débarque sur une petite île près de Hong-Kong pour éxécuter un chef mafieux mais il s'y retrouve coincé à cause d'un typhon. Il rencontre par la suite une agent de police à qui il cache sa véritable identité qui l'aidera à se loger. S'engagera dès lors une chasse à l'homme impitoyable confrontant la pègre locale à notre tueur à gages, sa nouvelle amie et ses collègues.","kung-fu, kung fu, assassin, fight scenes, martial .","https://ayr-streaming.herokuapp.com/image-uploads/legendary%20assassin.jpg","https://ayr-streaming.herokuapp.com/video-uploads/Legendary%20Assassin%20-%20Trailer.mp4");
        Movies m5=new Movies(2,"joker","crime/drame","2019","122","Dans les années 1980, à Gotham City, Arthur Fleck, un comédien de stand-up raté est agressé alors qu'il ère dans les rues de la ville déguisé en clown. Méprisé de tous et bafoué, il bascule peu à peu dans la folie pour devenir le Joker, un dangereux tueur psychotique.","mental illness, madness, joaquin phoenix","https://ayr-streaming.herokuapp.com/image-uploads/joker.jpg","https://ayr-streaming.herokuapp.com/video-uploads/JOKER%20-%20Final%20Trailer%20-%20Now%20Playing%20In%20Theaters.mp4");
        Movies m6=new Movies(5,"legendary assassin","action/arts martiaux","2008","100","Bo (Wu Jing) débarque sur une petite île près de Hong-Kong pour éxécuter un chef mafieux mais il s'y retrouve coincé à cause d'un typhon. Il rencontre par la suite une agent de police à qui il cache sa véritable identité qui l'aidera à se loger. S'engagera dès lors une chasse à l'homme impitoyable confrontant la pègre locale à notre tueur à gages, sa nouvelle amie et ses collègues.","kung-fu, kung fu, assassin, fight scenes, martial .","https://ayr-streaming.herokuapp.com/image-uploads/legendary%20assassin.jpg","https://ayr-streaming.herokuapp.com/video-uploads/Legendary%20Assassin%20-%20Trailer.mp4");
        allmovies.add(m1);
        allmovies.add(m2);
        allmovies.add(m3);
        allmovies.add(m4);
        allmovies.add(m5);
        allmovies.add(m6);
        model.notifyDataSetChanged();

    }

    public void playVid(Movies mm){

    }
}