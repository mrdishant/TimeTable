package com.nearur.timetable;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class Attendance extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ContentResolver resolver;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        listView = (ListView) findViewById(R.id.listattendance);
        arrayAdapter = new ArrayAdapter<String>(Attendance.this, android.R.layout.simple_list_item_1);
        sharedPreferences = getSharedPreferences("Time", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        resolver = getContentResolver();
        display();
    }

    void init() {
        final Dialog d = new Dialog(Attendance.this);
        d.setContentView(R.layout.dialog);
        d.setCancelable(false);
        Button btn = (Button) d.findViewById(R.id.buttonsubmit);
        final EditText name = (EditText) d.findViewById(R.id.editTextname);
        final EditText number = (EditText) d.findViewById(R.id.editTextnumber);
        AutoCompleteTextView autoCompleteTextView=d.findViewById(R.id.auto);
        autoCompleteTextView.setVisibility(View.GONE);
        name.setHint("Enter Number of Subjects");
        name.setInputType(InputType.TYPE_CLASS_NUMBER);
        number.setInputType(InputType.TYPE_CLASS_TEXT);
        number.setHint("Enter names of Subjects (Comma Seperated)");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(name.getText().toString().trim());
                String[] subjects = number.getText().toString().split(",");
                if (subjects.length != num) {
                    Toast.makeText(Attendance.this, "Please Check Details", Toast.LENGTH_LONG).show();
                } else {
                    Uri x = null;
                    for (int i = 1; i <= num; i++) {
                        ContentValues values = new ContentValues();
                        values.put(Util.sname, subjects[i - 1]);
                        x = resolver.insert(Util.u1, values);
                    }
                    Toast.makeText(Attendance.this, "Subjects : " + x.getLastPathSegment(), Toast.LENGTH_LONG).show();
                    editor.putString("class", "Added");
                    editor.commit();
                    d.dismiss();
                    display();
                }
            }
        });
        d.show();
}

    void display(){
        String[] p={"Name","Attended","Missed","Total"};
        Cursor c=resolver.query(Util.u1,p,null,null,null);
        if(c!=null){
            int i=1;
            while (c.moveToNext()){
                float per=0.0f;
                float a=c.getInt(1);
                float t=c.getInt(3);
                if(t>0){
                    per=(a/t)*100;
                }
               arrayAdapter.add((i++)+"\n"+p[0]+" : "+c.getString(0)+"\n"+p[1]+" : "+c.getInt(1)+"\n"+p[2]+" : "+c.getInt(2)+"\n"+p[3]+" : "+c.getInt(3)+"\n"+"Percentage : "+Math.round(per));
            }
        }
        if(arrayAdapter.isEmpty()){
            init();
        }
        c.close();
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Attendance.this);
                builder.setTitle("Attendance");
                builder.setMessage(arrayAdapter.getItem(i));
                builder.create().show();
            }
        });
    }
}
