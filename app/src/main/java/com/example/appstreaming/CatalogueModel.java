package com.example.appstreaming;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CatalogueModel extends ArrayAdapter<Movies> {
    private List<Movies> listItems;
    private int resource;

    public CatalogueModel(@NonNull Context context, int resource, List<Movies> data) {
        super(context, resource, data);
        Log.i("size",String.valueOf(data.size()));
        this.listItems=data;
        this.resource=resource;
    }
    @Override @NonNull
    public View getView(int pos, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem=convertView;
        if(listItem==null)
            listItem= LayoutInflater.from(getContext()).inflate(resource,parent,false);
        ImageView imageView=listItem.findViewById(R.id.movie_Image_Picture);
        Button movieTitle=listItem.findViewById(R.id.movie_Title);
        movieTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("demarer","1 demarage du player");
                Intent intent=new Intent(getContext(),VideoPlayer.class);
                Log.i("demarer","2 recuperation du film actuel");
                intent.putExtra("movie",listItems.get(pos));
                Log.i("demarer","3 envoi de l'intent");
                getContext().startActivity(intent);
                Log.i("demarer","4 recuperation du player");
            }
        });
        String title=listItems.get(pos).getName();
        String imgpath=listItems.get(pos).getImgpath();
        if(title!=null){
            //imageView.setImageResource(imgpath);
            Picasso.get().load(imgpath).into(imageView);
            movieTitle.setText(String.valueOf(title));



        }
        return listItem;
    }
}
