package com.nearur.timetable;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ContentResolver resolver;
    ListView listView;
    ArrayAdapter<String> adapter;
    String []days={"sunday","monday","tuesday","wednesday","thursday","friday","saturday"};
    String  today;
    TextView textView,textView2;
    ImageView imageView;
    Dialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View v = navigationView.getHeaderView(0);
        imageView=(ImageView)v.findViewById(R.id.imageViewuser);
        textView=(TextView)v.findViewById(R.id.navigationtext);
        textView2=(TextView)findViewById(R.id.textView2);

        d=new Dialog(MainActivity.this);
        resolver=getContentResolver();
        Calendar c=Calendar.getInstance();
        int a=c.get(Calendar.DAY_OF_WEEK);
        first();
        today=days[a-1];
        init(today);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Online.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);//
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),0254);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i=new Intent(MainActivity.this,New.class);
            startActivityForResult(i,7623);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.monday) {
            init("monday");
        } else if (id == R.id.tuesday) {
            init("tuesday");
        } else if (id == R.id.wednesday) {
            init("wednesday");
        } else if (id == R.id.thursday) {
            init("thursday");
        } else if (id == R.id.friday) {
            init("friday");
        } else if (id == R.id.saturday) {
            init("saturday");
        }else if(id==R.id.sunday){
            init("sunday");
        }else if(id==R.id.setup){
            Intent i=new Intent(MainActivity.this,Attendance.class);
            startActivity(i);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void first(){
        sharedPreferences=getSharedPreferences("Time",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        if(!sharedPreferences.contains("class")){
            final Dialog d=new Dialog(MainActivity.this);
            d.setContentView(R.layout.dialog);
            d.setCancelable(false);
            Button btn=(Button)d.findViewById(R.id.buttonsubmit);
            final EditText name=(EditText)d.findViewById(R.id.editTextname);
            final EditText number=(EditText)d.findViewById(R.id.editTextnumber);
            final AutoCompleteTextView autoCompleteTextView=(AutoCompleteTextView)d.findViewById(R.id.auto);
            ArrayAdapter<String>adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item);
            adapter.add("D3CSEA1");
            adapter.add("D3CSEA2");
            autoCompleteTextView.setAdapter(adapter);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editor.putString("name",name.getText().toString().trim());
                    editor.putInt("number",Integer.parseInt(number.getText().toString().trim()));
                    editor.putString("class",autoCompleteTextView.getText().toString().toUpperCase().trim());
                    editor.commit();
                    Uri x=null;
                    for (int i=1;i<=Integer.parseInt(number.getText().toString().trim()) ; i++){
                        ContentValues values=new ContentValues();
                        values.put(Util.number,i);
                        x=resolver.insert(Util.u,values);
                    }
                    Toast.makeText(MainActivity.this,"Lectures : "+x.getLastPathSegment(),Toast.LENGTH_LONG).show();

                    for (int i=1;i<=8;i++){
                        final String w="Number = "+i;
                        String Url="https://missnainathaman.000webhostapp.com/getData.php?id="+autoCompleteTextView.getText().toString().toUpperCase().trim()+"&lec="+i;
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

                                    for(int i=1;i<=5;i++){
                                        String[] str=r.getString(days[i]).split("\n");
                                        lecture.name=str[0].trim();
                                        lecture.tname=str[1];
                                        lecture.room=str[2];
                                        lecture.type=str[3];
                                        values.put(days[i],lecture.toString());
                                        values1.put(Util.sname,str[0].trim());
                                        resolver.insert(Util.u1,values1);
                                    }
                                    resolver.update(Util.u,values,w,null);
                                }catch (Exception e){

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                        );
                        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
                        requestQueue.add(request);
                    }
                    d.dismiss();
                    init(today);
                }
            });
            d.show();
        }
        textView.setText(sharedPreferences.getString("name","Click on Image")+"\n"+sharedPreferences.getString("class",""));

    }
    void init(String day){
        textView2.setVisibility(View.VISIBLE);
        listView=(ListView)findViewById(R.id.listtoday);
        adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1);
        String[] p={day};
        for (int i=1;i<=sharedPreferences.getInt("number",0);i++){
            String w="Number = "+i;
            Cursor cursor=resolver.query(Util.u,p,w,null,null);
            if(cursor!=null) {
                cursor.moveToNext();
                if(cursor.getString(0)!=null){
                    textView2.setVisibility(View.GONE);
                    adapter.add(i+"\n"+cursor.getString(0));
                }
            }
            cursor.close();
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i1, long l) {
                String sub = adapter.getItem(i1);
                final String w = "Name=\"" + sub.substring(sub.indexOf(":") + 1, sub.indexOf("Teacher")).trim() + "\"";
                String[] p = {"Name", "Attended", "Missed", "Total"};
                    final Cursor c = resolver.query(Util.u1, p, w, null, null);
                    if (c != null) {
                        c.moveToNext();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Have you Attended this Lecture");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try{int att = c.getInt(1);
                            int total = c.getInt(3);
                            ContentValues values = new ContentValues();
                            values.put(Util.attended, ++att);
                            values.put(Util.total, ++total);
                            int x = resolver.update(Util.u1, values, w, null);
                            Toast.makeText(MainActivity.this, "Cool,Study is Required", Toast.LENGTH_LONG).show();
                            c.close();}catch (Exception e){
                                Toast.makeText(MainActivity.this,"Subject Name not Matching\nLong Press To Update",Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try{int att = c.getInt(2);
                            int total = c.getInt(3);
                            ContentValues values = new ContentValues();
                            values.put(Util.missed, ++att);
                            values.put(Util.total, ++total);
                            resolver.update(Util.u1, values, w, null);
                            Toast.makeText(MainActivity.this, "Enjoying Day!!", Toast.LENGTH_LONG).show();
                            c.close();}catch (Exception e){
                                Toast.makeText(MainActivity.this,"Subject Name not Matching\nLong Press To Update",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    builder.setTitle("Lecture :" + (i1 + 1));
                    d=builder.create();
                    d.show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MainActivity.this,New.class);
                d.dismiss();
                intent.putExtra("Number",i+1);
                startActivityForResult(intent,7623);
                return false;
            }
        });
        if(sharedPreferences.contains("image")) {
            byte[] imageAsBytes = Base64.decode(sharedPreferences.getString("image", "").getBytes(), 0);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==7623&&resultCode==-7623){
            d.dismiss();
            init(today);
        }
        if (requestCode == 0254)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    try
                    {

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(MainActivity.this.getContentResolver(), data.getData());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                        byte[] b = baos.toByteArray();
                        String encoded = Base64.encodeToString(b, Base64.DEFAULT);
                        editor.putString("image",encoded);
                        editor.commit();
                        imageView.setImageBitmap(bitmap);

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }
            } else if (resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }
    }

