package com.rapha.notes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notes;
    static ArrayAdapter<String> adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note,menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(getApplicationContext(),notes.class);

        intent.putExtra("note_id",-1);

        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.list);

        final Context context = this;

        notes = new ArrayList<>();

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.rapha.notes", Context.MODE_PRIVATE);

        try {
            notes = (ArrayList<String>)ObjectSerializer.deserialize(sharedPreferences.getString("notes",ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(notes.isEmpty())
        {
            notes.add("Add a note");
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,notes);

        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {

                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.alert_light_frame)
                        .setTitle("Delete Note")
                        .setMessage("Are you sure you want to delete this note?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove((int)id);
                                adapter.notifyDataSetChanged();

                                SharedPreferences preferences = getSharedPreferences("com.rapha.notes", Context.MODE_PRIVATE);

                                try {
                                    preferences.edit().putString("notes",ObjectSerializer.serialize(MainActivity.notes)).apply();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).show();
                return  true;
                }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),notes.class);

                intent.putExtra("note_id",(int)id);

                startActivity(intent);
            }
        });
    }
}
