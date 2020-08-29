package com.example.developerguideapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        ArrayList<String> message = intent.getStringArrayListExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = findViewById(R.id.textView);
        //textView.setText("" + message.size());
        StringBuilder output = new StringBuilder("");
        for (String mes : message) {
            //textView.setText(mes);
            output.append(mes);
            output.append("\n");
        }
        textView.setText(output);
    }



}