package com.example.drawingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private CustomViews customViews;
    float sizes = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customViews = (CustomViews) findViewById(R.id.customView);
    }

    public void clearCanvas(View view) {

        customViews.clearCanvas();
    }

    public void paintClicked(View view) {

        String color = view.getTag().toString();
        System.out.println(color);
        customViews.setColor(color);
    }

    public void increase_size(View view) {
        if (sizes < 150) {
            sizes = sizes + 2;
        }

        setStroke();
    }

    public void decrease_size(View view) {
        if (sizes > 8) {
            sizes = sizes - 2;
        }
        setStroke();
    }

    public void setStroke() {
        customViews.setnewStroke(sizes);
    }


}
