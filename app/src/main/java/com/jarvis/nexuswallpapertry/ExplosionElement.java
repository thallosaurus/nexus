package com.jarvis.nexuswallpapertry;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Jarvis on 23.03.2017.
 */

public class ExplosionElement {

    public float upX, upY, downX, downY, leftX, leftY, rightX, rightY;
    Color c;
    int o;

    Paint paint = new Paint();
    public ExplosionElement(float paramUX, float paramUY, float paramDX, float paramDY, float paramLX, float paramLY, float paramRX, float paramRY) {
        upX = paramUX;
        upY = paramUY;
        downX = paramDX;
        downY = paramDY;
        leftX = paramLX;
        leftY = paramLY;
        rightX = paramRX;
        rightY = paramRY;
    }

    public ExplosionElement(float x, float y, int color) {
        upX = x;
        upY = y;
        downX = x;
        downY = y;
        leftX = x;
        leftY = y;
        rightX = x;
        rightY = y;
        paint.setColor(color);
    }

    public ExplosionElement(float x, float y, int color, int opacity) {
        upX = x;
        upY = y;
        downX = x;
        downY = y;
        leftX = x;
        leftY = y;
        rightX = x;
        rightY = y;
        paint.setColor(color);
        o = opacity;
        paint.setAlpha(o);
    }
}
