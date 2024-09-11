package com.eugene.tabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {
    private Context context;
    private int resource;
    private List<String> items;
    private Typeface typeface;

    public CustomAdapter(Context context, int resource, List<String> items, Typeface typeface) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
        this.typeface = typeface;
    }

    @SuppressLint({"ViewHolder", "ResourceAsColor"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(items.get(position));
        textView.setTextColor(Color.parseColor("#ffa012"));
        textView.setTypeface(typeface);

        return convertView;
    }
}
