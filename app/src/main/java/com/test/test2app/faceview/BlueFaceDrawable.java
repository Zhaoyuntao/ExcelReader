package com.test.test2app.faceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * created by zhaoyuntao
 * on 2020/7/14
 * description:
 */
public class BlueFaceDrawable extends Drawable {
    private final Paint paint = new Paint();
    private int colorBack = -1;
    private final Context context;
    private String name;
    private float percentText;
    private PaintFlagsDrawFilter paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    public BlueFaceDrawable(Context context, String name, int colorBack, float percentText) {
        this.context = context;
        this.name = name;
        this.colorBack = colorBack;
        this.percentText = percentText;
    }

    public BlueFaceDrawable(Context context, String name) {
        this.context = context;
        this.name = name;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.setDrawFilter(paintFlagsDrawFilter);
        DefaultFaceUtils.draw(canvas, name, context, colorBack, percentText);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
