package com.example.assignment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class CustomAdapter extends ArrayAdapter<String> {

    Context c;
    String[] categoryType;
    int[] images;

    public CustomAdapter(Context context, String[] types, int[] images){
        super(context, R.layout.spinner_item, types);
        this.c = context;
        this.categoryType = types;
        this.images = images;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_item, null);
        TextView t1 = (TextView) row.findViewById(R.id.spinner_textView);
        ImageView image = (ImageView) row.findViewById(R.id.spinner_imageView);
        t1.setText(categoryType[position]);
        image.setImageResource(images[position]);

        return row;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_item, null);
        TextView t1 = (TextView) row.findViewById(R.id.spinner_textView);
        ImageView image = (ImageView) row.findViewById(R.id.spinner_imageView);
        t1.setText(categoryType[position]);
        image.setImageResource(images[position]);

        return row;
    }
}
