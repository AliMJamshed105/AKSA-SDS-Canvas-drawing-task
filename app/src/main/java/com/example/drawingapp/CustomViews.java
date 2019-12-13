package com.example.drawingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CustomViews extends View {

    //Vector<modelDrawing> modelDrawings = new Vector<modelDrawing>();
    modelDrawing modelDrawing = new modelDrawing();
    public Boolean Eraser = false;
    public float strokewidth = 8f;
    private int paintColour = 0xFF660000;
    private Bitmap bitmap;
    private Canvas canvas;
    private Path path;
    private ArrayList<Path> paths = new ArrayList<Path>();
    private ArrayList<modelDrawing> modelDrawings = new ArrayList<modelDrawing>();
    private Paint paint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    Context context;
    private Map<Path, Integer> colorsMap = new HashMap<Path, Integer>();

    public  CustomViews(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
        this.context = context;
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(strokewidth);


    }

    public void  setColor(String newColor){
        invalidate();
        paintColour = Color.parseColor(newColor);
        paint.setColor(paintColour);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w,h,oldw,oldh);

        bitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        canvas=new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*
        for (Path p : paths)
        {
            paint.setColor(colorsMap.get(p));
            canvas.drawPath(p, paint);
        }
         */

        for(modelDrawing drawing : modelDrawings)
        {
            Path p=drawing.getPath();
            Paint p1 = drawing.getpaint();
            p1.setStrokeWidth(drawing.getStrokewidth());
            p1.setColor(colorsMap.get(p));
            canvas.drawPath(p, p1);
        }

        paint.setStrokeWidth(strokewidth);
        paint.setColor(paintColour);
        canvas.drawPath(path,paint);

    }

    public void setnewStroke(float newstroke)
    {
        strokewidth = newstroke;
        System.out.println("stroke width new "+ paint.getStrokeWidth());
    }

    private void onStartTouch(float x,float y){
        path.moveTo(x,y);
        mX=x;
        mY=y;
    }
    private void moveTouch(float x, float y)
    {
        float dx = Math.abs(x-mX);
        float dy = Math.abs(y-mY);
        if(dx >= TOLERANCE || dy >= TOLERANCE)
        {
            path.quadTo(mX,mY,(x+mX)/2, (y+mY)/2);
            mX=x;
            mY=y;
        }
    }


    public void clearCanvas()
    {
        path.reset();
        paths.clear();
        modelDrawings.clear();
        invalidate();
    }

    private void upTouch(){
        path.lineTo(mX,mY);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        modelDrawing modelDrawing = new modelDrawing();
        float x = event.getX();
        float y = event.getY();


        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                onStartTouch(x, y);
                invalidate();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                moveTouch(x,y);
                invalidate();
                break;
            }
            case MotionEvent.ACTION_UP:{
                //paths.add(path);
                colorsMap.put(path, paintColour);
                upTouch();
                paths.add(path);
                modelDrawing.setPath(path);;
                modelDrawing.setPaint(paint);
                modelDrawing.setStrokewidth(paint.getStrokeWidth());
                modelDrawings.add(modelDrawing);
                path= new Path();
                invalidate();
                break;
            }

        }
        return true;
    }
}
