package com.jarvis.nexuswallpapertry;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Jarvis on 22.03.2017.
 */

public class CanvasView extends View {

    ArrayList<ExplosionElement> ee = new ArrayList<>();

    private int sizeFactor = 50;
    Context context;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Path mPath;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    private int height, width;
    int defaultAlpha = 255;
    //int background = Color.argb(255, 27, 20, 44);
    int background = R.color.background;
            //27, 20, 44

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;


        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        height = h;
        width = w;
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        invalidate();

        /*switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                parseElement(event.getX(), event.getY());
                break;
        } */

        //switch (event.getPointerCount()) {
        if (event.getPointerCount() == 1) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    parseElement(event.getX(0), event.getY(0));
                    break;
            }
        } else if (event.getPointerCount() >= 2) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_POINTER_DOWN:
                        Log.d("Debug", "Multi Touch Event, Count: " + event.getPointerCount());
                        for (int i = 0; i < event.getPointerCount(); i++) {
                            parseElement(event.getX(i), event.getY(i));
                        }
                        Log.v("Touch", event.toString());
                        break;
                }
        }
        return true;
    }

    private void parseElement(float x, float y) {
        //Color
        Paint pt = new Paint();
        Random rnd = new Random();
        pt.setARGB(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        ee.add(new ExplosionElement(x, y, pt.getColor(), defaultAlpha));
        //Log.v("Element", Float.toString(ee.get(ee.size()-1).leftX));
    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);

        //Setting Background
        c.drawColor(getResources().getColor(background));

        for (int i = 0; i < ee.size(); i++) {
            ExplosionElement el = ee.get(i);
                Paint p = el.paint;
                c.drawRect(el.leftX, el.leftY, el.leftX + sizeFactor, el.leftY + sizeFactor, p);
                c.drawRect(el.rightX, el.rightY, el.rightX + sizeFactor, el.rightY + sizeFactor, p);
                c.drawRect(el.upX, el.upY, el.upX + sizeFactor, el.upY + sizeFactor, p);
                c.drawRect(el.downX, el.downY, el.downX + sizeFactor, el.downY + sizeFactor, p);
        }
        garbageCollector();
        animateIt(c);
    }

    private void garbageCollector() {
        if (ee.size() != 0) {
            ExplosionElement el = ee.get(0);

            if (el.upY + sizeFactor + (sizeFactor * 2) < 0 && el.downY > height && el.leftX + sizeFactor + (sizeFactor * 2) < 0 && el.rightX > width) {
                ee.remove(0);
                Log.v("Garbage Collector", "Deleted first Element, New Size: " + ee.size());
            }
        }
    }

    private void animateIt(Canvas c) {
        for (int i = 0; i < ee.size(); i++) {
            ExplosionElement el = ee.get(i);
            el.upY -=5;
            el.downY +=5;
            el.leftX -=5;
            el.rightX +=5;
        }


        invalidate();
    }
}
