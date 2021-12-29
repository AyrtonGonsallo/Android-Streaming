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

public class SeriesModel extends ArrayAdapter<Series> {
    private List<Series> listItems;
    private int resource;
    
    public SeriesModel(@NonNull Context context, int resource, List<Series> data) {
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
            ImageView imageView=listItem.findViewById(R.id.serie_Image_Picture);
            Button serieTitle=listItem.findViewById(R.id.serie_Title);
            serieTitle.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
            Log.i("demarer","1 serie");
            Intent intent=new Intent(getContext(),SeriesSeasons.class);
            Log.i("demarer","2 recuperation de la serie actuelle");
            intent.putExtra("serie",listItems.get(pos));
            Log.i("demarer","3 envoi de l'intent");
            getContext().startActivity(intent);

            }
            });
            String title=listItems.get(pos).getName();
            String imgpath=listItems.get(pos).getImgpath();
            if(title!=null){
            //imageView.setImageResource(imgpath);
            Picasso.get().load(imgpath).into(imageView);
            serieTitle.setText(String.valueOf(title));
    
    
    
            }
            return listItem;
            }
}
