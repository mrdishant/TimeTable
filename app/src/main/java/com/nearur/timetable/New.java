package com.nearur.timetable;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class New extends AppCompatActivity {

    EditText number,tname,room,sname;
    AutoCompleteTextView weekday;
    RadioButton l,t,p;
    Button Submit;
    ArrayAdapter<String> adapter;
    ContentResolver resolver;
    Lecture lecture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        resolver=getContentResolver();
        init();
        adapter=new ArrayAdapter<String>(New.this,android.R.layout.simple_spinner_dropdown_item);
        adapter.add("Monday");
        adapter.add("Tuesday");
        adapter.add("Wednesday");
        adapter.add("Thursday");
        adapter.add("Friday");
        adapter.add("Saturday");
        adapter.add("Sunday");
        weekday.setAdapter(adapter);
        Submit=(Button)findViewById(R.id.buttondone);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(number.getText().toString().length()>0&&sname.getText().toString().length()>0&&tname.getText().toString().length()>0&&weekday.getText().toString().length()>0&&room.getText().toString().length()>0&&(l.isChecked()||p.isChecked()||t.isChecked())){
                    update();
                    setResult(-7623);
                    finish();
                }else{
                    Toast.makeText(New.this,"Please Fill All Details",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void init(){

        number=(EditText)findViewById(R.id.editTextnumber);
        int no=getIntent().getIntExtra("Number",0);
        if(no>0){
            number.setText(no+"");
        }
        sname=(EditText) findViewById(R.id.editTextname);
        tname=(EditText)findViewById(R.id.editTextteacher);
        room=(EditText)findViewById(R.id.editTextroom);
        weekday=(AutoCompleteTextView) findViewById(R.id.editTextday);

        l=(RadioButton)findViewById(R.id.radioButtonL);
        t=(RadioButton)findViewById(R.id.radioButtonT);
        p=(RadioButton)findViewById(R.id.radioButtonP);
    }
    void update(){
        if(l.isChecked()){
            lecture=new Lecture(sname.getText().toString(),tname.getText().toString(),room.getText().toString(),"Lecture");
        }else if(p.isChecked()){
            lecture=new Lecture(sname.getText().toString(),tname.getText().toString(),room.getText().toString(),"Practical");
        }else{
            lecture=new Lecture(sname.getText().toString(),tname.getText().toString(),room.getText().toString(),"Tutorial");
        }
        int n=Integer.parseInt(number.getText().toString());
        ContentValues values=new ContentValues();
        values.put(weekday.getText().toString().toLowerCase().trim(),lecture.toString());
        String w="Number = "+n;
        int x=resolver.update(Util.u,values,w,null);
        Toast.makeText(New.this,"Lecture Updated :"+n,Toast.LENGTH_LONG).show();
    }
}
