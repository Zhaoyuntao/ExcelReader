package com.test.test2app.faceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;

/**
 * created by zhaoyuntao
 * on 2020/7/14
 * description:
 */
class BlueFaceDrawable extends android.graphics.drawable.Drawable {
    private final Paint paint = new Paint();
    private final Context context;
    private String name;
    private PaintFlagsDrawFilter paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    public BlueFaceDrawable(Context context, String name) {
        this.context = context;
        this.name = name;
    }

    @Override
    public void draw(@androidx.annotation.NonNull Canvas canvas) {
        canvas.setDrawFilter(paintFlagsDrawFilter);
        DefaultFaceUtils.draw(canvas, name, context);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@androidx.annotation.Nullable android.graphics.ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
