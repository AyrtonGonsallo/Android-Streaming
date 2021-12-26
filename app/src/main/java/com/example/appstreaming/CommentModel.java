package com.example.appstreaming;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class CommentModel extends ArrayAdapter<Comments> {
    private List<Comments> listItems;
    private int resource;

    public CommentModel(@NonNull Context context, int resource, List<Comments> data) {
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

        TextView textC=listItem.findViewById(R.id.textC);
        TextView dateC=listItem.findViewById(R.id.dateC);
        TextView userC=listItem.findViewById(R.id.userC);
        TextView cid=listItem.findViewById(R.id.comId);
        textC.setText(listItems.get(pos).getText());
        dateC.setText(listItems.get(pos).getDate().toString());
        userC.setText("User "+listItems.get(pos).getUid());
        cid.setText("nº"+listItems.get(pos).getCid());
        return listItem;
    }
}
