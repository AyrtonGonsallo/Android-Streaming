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
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SeasonsModel extends ArrayAdapter<Seasons> {
    private List<Seasons> listItems;
    private int resource;

    public SeasonsModel(@NonNull Context context, int resource, List<Seasons> data) {
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
        ImageView imageView=listItem.findViewById(R.id.season_Image_Picture);
        Button serieTitle=listItem.findViewById(R.id.season_Title);
        TextView desc=listItem.findViewById(R.id.seasonD);
        TextView nep=listItem.findViewById(R.id.seasonNEP);
        serieTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("demarer","1 season");
                Intent intent=new Intent(getContext(),SeasonsEpisodes.class);
                Log.i("demarer","2 recuperation de la serie actuelle");
                intent.putExtra("season",listItems.get(pos));
                Log.i("demarer","3 envoi de l'intent");
                getContext().startActivity(intent);

            }
        });
        String title=listItems.get(pos).getName();
        String imgpath=listItems.get(pos).getImgpath();
        desc.setText(listItems.get(pos).getDescription());
        nep.setText("total: "+listItems.get(pos).getNep()+" episods");
        if(title!=null){
            //imageView.setImageResource(imgpath);
            Picasso.get().load(imgpath).into(imageView);
            serieTitle.setText(String.valueOf(title));



        }
        return listItem;
    }
}
