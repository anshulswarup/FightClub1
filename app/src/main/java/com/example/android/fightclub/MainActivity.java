package com.example.android.fightclub;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.fightclub.Data.Movie;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final static String db_URL = "https://project-366378921237533093.firebaseio.com";
    EditText nameTxt, descTxt;
    Button saveBtn;
    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<String> names = new ArrayList<>();
    Firebase fire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv =(ListView)  findViewById(R.id.lv);

        fire = new Firebase(db_URL);
        this.retrieveData();
        this.showDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//show Dialog
    private void showDialog()
    {
        Dialog d = new Dialog(this);
        d.setTitle("Save Online");
        d.setContentView(R.layout.dialoglayout);
        nameTxt = (EditText) d.findViewById(R.id.nameEditText);
        descTxt = (EditText) d.findViewById(R.id.descEditText);
        saveBtn = (Button) d.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData(nameTxt.getText().toString(),descTxt.getText().toString());
                nameTxt.setText("");
                descTxt.setText("");

            }
        });
        d.show();
    }

    //add Data

    private void addData(String name, String desc)
    {
        Movie m = new Movie();
        m.setName(name);
        m.setDescription(desc);
        fire.child("Movie").push().setValue(m);
    }

    //Retreive
    private void retrieveData()
    {
        fire.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
getUpdates(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
getUpdates(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void getUpdates(DataSnapshot ds)
    {
        names.clear();
        for (DataSnapshot data : ds.getChildren())
        {
            Movie m = new Movie();
            m.setName(data.getValue(Movie.class).getName());
            names.add(m.getName());
        }
        if(names.size()>0)
        {
            adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,names);
            lv.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(MainActivity.this,"No Data",Toast.LENGTH_SHORT).show();
        }
    }
}
