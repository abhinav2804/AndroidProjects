package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view){
        int numberOfCoffees = 3;
        display(numberOfCoffees);
    }

    private void display(int num) {
        TextView Tview2 = (TextView) findViewById(R.id.no_of_coffee);
        Tview2.setText("" + num);
        num = 15 * num;
        displayPrice(num);
    }

    private void displayPrice(int num) {
        TextView Tview = (TextView) findViewById(R.id.total_amount);
        Tview.setText(NumberFormat.getCurrencyInstance(new Locale("en", "in")).format(num));
    }
}