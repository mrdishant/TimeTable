package com.nearur.timetable;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrdis on 8/25/2017.
 */

public class MyAdapter extends ArrayAdapter<String> {

    Context context; int resource; ArrayList<String> objects;
    public MyAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view= LayoutInflater.from(context).inflate(resource,parent,false);

        String s=objects.get(position);

        CheckedTextView checkedTextView=(CheckedTextView)view.findViewById(R.id.checkedTextViewstring);
        checkedTextView.setText(s);

        return view;
    }


}
