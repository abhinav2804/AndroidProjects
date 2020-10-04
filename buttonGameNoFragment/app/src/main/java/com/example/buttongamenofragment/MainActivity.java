package com.example.buttongamenofragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    float dx, dy;
    int x, y;
    ConstraintLayout cl = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*final Button button = (Button) findViewById(R.id.my_button);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Random R = new Random();
                        final float dx = R.nextFloat() * displayMetrics.widthPixels;
                        final float dy = R.nextFloat() * displayMetrics.heightPixels;
                        final Timer timer = new Timer();
                        button.animate().x(dx).y(dy).setDuration(800).start();
                    }
                });
            }
        }, 0, 100);*/
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        dx = displayMetrics.widthPixels;
        dy = displayMetrics.heightPixels;
        x = (int)dx - 240;
        y = (int)dy - 200;

        cl = (ConstraintLayout) findViewById(R.id.constraintLayoutId);
        cl.setOnTouchListener(new View.OnTouchListener() {
            TextView score = (TextView) findViewById(R.id.score);
            Button bt = (Button) findViewById(R.id.my_button);

            Random rand = new Random();


            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int tx = (int) motionEvent.getX();
                int ty = (int) motionEvent.getY();
                int btx = (int) bt.getX();
                int bty = (int) bt.getY();
                boolean flag = true;
                if (ty == bty && tx == btx) {
                    int rand_int1 = rand.nextInt(x);
                    int rand_int2 = rand.nextInt(y);

                    //score.setText(""+rand_int1);
                    //bt.getLocationOnScreen(posXY);
                    bt.setX(rand_int1);
                    bt.setY(rand_int2);
                    String s = score.getText().toString();
                    int i = Integer.parseInt(s);
                    score.setText("" + (i + 1));
                } else {
                    String s = score.getText().toString();
                    int i = Integer.parseInt(s);
                    score.setText("" + (i - 1));
                    return false;
                }

                return true;
            }
        });

    }

    public void increment(View view) {
        TextView score = (TextView) findViewById(R.id.score);
        Button bt = (Button) findViewById(R.id.my_button);

        Random rand = new Random();
        
        int rand_int1 = rand.nextInt(x);
        int rand_int2 = rand.nextInt(y);

        while (rand_int2 <= score.getY() + score.getHeight()) {
            rand_int2 = rand.nextInt(y);
        }

        //score.setText(""+rand_int1);
        //bt.getLocationOnScreen(posXY);
        bt.setX(rand_int1);
        bt.setY(rand_int2);
        String s = score.getText().toString();
        int i = Integer.parseInt(s);
        score.setText("" + (i + 1));
    }

}