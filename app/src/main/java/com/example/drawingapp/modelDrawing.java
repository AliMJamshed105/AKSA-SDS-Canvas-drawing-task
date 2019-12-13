package com.example.drawingapp;

import android.graphics.Paint;
import android.graphics.Path;

public class modelDrawing {
    public Paint paint;
    public Path path;
    float strokewidth;


    Paint getpaint()
    {
        return paint;
    }
    Path getPath()
    {
        return path;
    }
    float getStrokewidth()
    {return strokewidth;}
    void setPaint(Paint p1)
    {
        paint = p1;
    }
    void setPath(Path p2)
    {
        path = p2;
    }
    void setStrokewidth(float w)
    {
        strokewidth = w;
    }

}
