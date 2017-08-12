package com.nearur.timetable;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class Online extends AppCompatActivity {

    Button button;
    EditText editText;
    ContentResolver resolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        resolver=getContentResolver();
        editText=(EditText)findViewById(R.id.editTexto);
        button=(Button)findViewById(R.id.buttono);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=1;i<=8;i++){
                    final String w="Number = "+i;
                    String Url="https://missnainathaman.000webhostapp.com/getData.php?id="+editText.getText().toString().toUpperCase().trim()+"&lec="+i;
                    StringRequest request=new StringRequest(Url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonObject=new JSONObject(response);
                                JSONArray array=jsonObject.getJSONArray("result");
                                JSONObject r=array.getJSONObject(0);
                                ContentValues values=new ContentValues();
                                ContentValues values1=new ContentValues();
                                Lecture lecture=new Lecture("","","","");
                                String[] str=r.getString("monday").split("\n");
                                lecture.name=str[0].trim();
                                lecture.tname=str[1];
                                lecture.room=str[2];
                                lecture.type=str[3];
                                values.put("monday",lecture.toString());
                                resolver.update(Util.u,values,w,null);
                                values1.put(Util.sname,str[0].trim());
                                resolver.insert(Util.u1,values1);

                            }catch (Exception e){

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
                    );
                    Toast.makeText(Online.this,"Done"+i,Toast.LENGTH_LONG).show();
                    RequestQueue requestQueue= Volley.newRequestQueue(Online.this);
                    requestQueue.add(request);
                }
            }
        });
    }
}
