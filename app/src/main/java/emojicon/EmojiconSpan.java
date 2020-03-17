/*
 * Copyright 2014 Hieu Rocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package emojicon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * @author Hieu Rocker (rockerhieu@gmail.com)
 * change by zepei align center
 */
//public class EmojiconSpan extends DynamicDrawableSpan {
//
//    private final Context mContext;
//    private final int mResourceId;
//    private final int mSize;
//
//    private Drawable mDrawable;
//
//    public EmojiconSpan(Context context, int resourceId, int size) {
//        super();
//        mContext = context;
//        mResourceId = resourceId;
//        mSize = size;
//    }
//
//    public Drawable getDrawable() {
//        if (mDrawable == null) {
//            try {
//                mDrawable = mContext.getResources().getDrawable(mResourceId);
//                int size = mSize;
//                mDrawable.setBounds(0, 0, size, size);
//            } catch (Exception e) {
//                // swallow
//            }
//        }
//        return mDrawable;
//    }
//}
public class EmojiconSpan extends DynamicDrawableSpan {

    private final int mResourceId;
    private final int mSize;
    private Context mContext;
    private WeakReference<Drawable> mDrawableRef;
    private Drawable mDrawable;

    public EmojiconSpan(Context context, int resourceId, int size) {
        super();
        mContext = context;
        mResourceId = resourceId;
        mSize = size;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end,
                       Paint.FontMetricsInt fontMetricsInt) {
        Drawable drawable = getDrawable();
        Rect rect = drawable.getBounds();
        if (fontMetricsInt != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.descent - fmPaint.ascent;
            int drHeight = rect.bottom - rect.top;
            int centerY = fmPaint.ascent + fontHeight / 2;

            fontMetricsInt.ascent = centerY - drHeight / 2;
            fontMetricsInt.top = fontMetricsInt.ascent;
            fontMetricsInt.bottom = centerY + drHeight / 2;
            fontMetricsInt.descent = fontMetricsInt.bottom;
        }
        return rect.right + 6;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y,
                     int bottom, Paint paint) {
        Drawable drawable = getCachedDrawable();
        canvas.save();
        Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
        int fontHeight = fmPaint.descent - fmPaint.ascent;
        int centerY = y + fmPaint.descent - fontHeight / 2;
        int transY = centerY - (drawable.getBounds().bottom - drawable.getBounds().top) / 2;
        canvas.translate(x + 3, transY);
        drawable.draw(canvas);
        canvas.restore();
    }

    private Drawable getCachedDrawable() {
        WeakReference<Drawable> wr = mDrawableRef;
        Drawable d = null;
        if (wr != null) {
            d = wr.get();
        }

        if (d == null) {
            d = getDrawable();
            mDrawableRef = new WeakReference<>(d);
        }

        return d;
    }

    @Override
    public Drawable getDrawable() {
        Drawable drawable = null;
        if (mDrawable != null) {
            drawable = mDrawable;
        } else {
            try {
                drawable = mContext.getResources().getDrawable(mResourceId);
                drawable.setBounds(0, 0, mSize, mSize);
            } catch (Exception e) {
                Log.i("emoji", "Unable to find resource: " + mResourceId);
            }
        }
        return drawable;
    }
}