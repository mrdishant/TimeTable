package com.nearur.timetable;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.Toast;
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
    public View getView(int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {

        final View v= LayoutInflater.from(context).inflate(resource,parent,false);

        String s=objects.get(position);

        final CheckedTextView checkedTextView=(CheckedTextView)v.findViewById(R.id.checkedTextViewstring);
        checkedTextView.setText(s);
        final ImageButton button=(ImageButton)v.findViewById(R.id.toolbarcard);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.showContextMenuForChild(view);
            }
        });
        return v;
    }

}
