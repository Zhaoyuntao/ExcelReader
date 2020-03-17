package com.test.test2app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;


import androidx.annotation.Nullable;

import com.test.test2app.Hanzi2Pinyin;
import com.test.test2app.utils.BitmapUtils;
import com.zhaoyuntao.androidutils.tools.TextMeasure;

import java.util.Random;

/**
 * created by zhaoyuntao
 * on 2019-12-16
 * description:
 */
public class DefaultFaceView extends ImageView {
    private int w_view;
    private int h_view;

    private float radius;

    private float textSize;

    private String name;

    private Paint paint;
    private PaintFlagsDrawFilter paintFlagsDrawFilter;
    private Hanzi2Pinyin hanzi2Pinyin;
    private int[] colors_start;
    private int[] colors_end;

    private Shader shader;

    public DefaultFaceView(Context context) {
        super(context);
        init();
    }

    public DefaultFaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DefaultFaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        textSize = BitmapUtils.sp2px(getContext(), 20);

        //paint and canvas
        paint = new Paint();
        paint.setAntiAlias(true);
        paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        setBackground(null);

        hanzi2Pinyin = Hanzi2Pinyin.getInstance(getContext());

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w_max = View.MeasureSpec.getSize(widthMeasureSpec);
        int h_max = View.MeasureSpec.getSize(heightMeasureSpec);
        if (w_max > 0) {
            radius = w_max / 2f;
            w_view = w_max;
            h_view = h_max;
            initShader();
        }
        setMeasuredDimension(w_max, h_max);
    }

    private void initShader() {
        if (!TextUtils.isEmpty(name)) {
            S.s("h:" + h_view);
            shader = new LinearGradient(0, 0, 0, h_view, getRandomColor(name), null, Shader.TileMode.REPEAT);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap=DefaultFaceUtils.getDefaultFace(getContext(),w_view,h_view,name);
        Rect rect=new Rect();
        rect.set(0,0,bitmap.getWidth(),bitmap.getHeight());
        Rect rect_des=new Rect();
        rect_des.set(0,0,w_view,h_view);
        canvas.drawBitmap(bitmap,rect,rect_des,paint);
        super.onDraw(canvas);
    }


    public void setName(String name) {
        if (TextUtils.isEmpty(name)) {
            this.name = null;
            return;
        }

        char firstOne = name.charAt(0);
        if (hanzi2Pinyin.isHanzi2(firstOne)) {
            this.name = String.valueOf(hanzi2Pinyin.hanziToPinyin(firstOne)[0].charAt(0));
        } else {
            this.name = String.valueOf(firstOne);
        }
        this.name = this.name.toUpperCase();

        initShader();

        postInvalidate();
    }

    /**
     * get a random color
     *
     * @param name
     * @return
     */
    private int[] getRandomColor(String name) {
        if (!TextUtils.isEmpty(name) && colors_start != null && colors_start.length > 0 && colors_end != null && colors_end.length > 0) {
            byte[] arr = Base64.encode(name.getBytes(), Base64.NO_WRAP);
            if (arr != null && arr.length > 0) {
                long sum = 0;
                for (byte number : arr) {
                    sum += number;
                }
                int index = new Random(sum).nextInt(Math.min(colors_start.length, colors_end.length));
                return new int[]{colors_start[index], colors_end[index]};
            }
        }
        return new int[]{Color.BLACK, Color.BLACK};
    }
}
