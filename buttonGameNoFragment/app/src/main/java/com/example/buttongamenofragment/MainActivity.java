package com.example.buttongamenofragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout cl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        TextView score = (TextView) findViewById(R.id.score);
        Button bt = (Button) findViewById(R.id.my_button);
        this.cl = (ConstraintLayout) findViewById(R.id.constraintLayoutId);
        cl.setMargins(100,100,100,100);
        bt.setLayoutParams(cl);
        String s = score.getText().toString();
        int i = Integer.parseInt(s);
        score.setText(""+(i+1));
    }

}