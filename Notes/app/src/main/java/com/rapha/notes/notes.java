package com.rapha.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.io.IOException;

public class notes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        EditText editText = (EditText)findViewById(R.id.nota);

        Intent intent = getIntent();
        int id = intent.getIntExtra("note_id",-2);

        if(id!=-1)
        {
            editText.setText(MainActivity.notes.get(intent.getIntExtra("note_id",-1)));
        }
    }

    @Override
    public void onBackPressed() {

        EditText editText = (EditText)findViewById(R.id.nota);
        String new_note = editText.getText().toString();

        Intent intent = getIntent();
        int id = intent.getIntExtra("note_id",-2);

        if(id==-1)
        {
            MainActivity.notes.add(new_note);
        }
        else
        {
            MainActivity.notes.set(id,new_note);
        }

        MainActivity.adapter.notifyDataSetChanged();

        SharedPreferences preferences = getSharedPreferences("com.rapha.notes", Context.MODE_PRIVATE);

        try {
            preferences.edit().putString("notes",ObjectSerializer.serialize(MainActivity.notes)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onBackPressed();
    }
}
