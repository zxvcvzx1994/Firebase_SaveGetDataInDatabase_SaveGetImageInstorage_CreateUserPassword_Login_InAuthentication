package com.example.sd.myapplication.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sd.myapplication.Module.Picture;
import com.example.sd.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kh on 7/11/2017.
 */

public class MyAdapter extends ArrayAdapter<Picture> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Picture> arrayList;
    private int resource;

    public MyAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Picture> list) {
        super(context, resource, list);
        this.context  = context;
        this.resource = resource;
        this.arrayList=  list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        MyViewHolder holder;
        if(view==null) {
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view =  inflater.inflate(resource, parent, false);
            holder = new MyViewHolder(view);
            view.setTag(holder);
        }else
            holder = (MyViewHolder) convertView.getTag();

        Picture picture =  arrayList.get(position);
        holder.name.setText(picture.getName());
        Picasso.with(context).load(picture.getPath()).placeholder(R.mipmap.aaa).into(holder.img);
        return view;
    }
    public   class MyViewHolder {
        @BindView(R.id.txtName)
        TextView name;
        @BindView(R.id.img)
         ImageView img;
        public MyViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}

