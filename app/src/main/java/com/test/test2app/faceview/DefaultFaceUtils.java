package com.test.test2app.faceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;


import com.test.test2app.R;
import com.zhaoyuntao.androidutils.tools.TextMeasure;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * created by zhaoyuntao
 * on 2019-12-17
 * description:
 */
class DefaultFaceUtils {

    private static int[] colors_start;
    private static int[] colors_end;
    private static int[] colors_default;
    private static final Pattern pattern = Pattern.compile("[a-zA-Z0-9]+");

    public static Bitmap getDefaultFace(Context context, int width, int height, String displayName) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        int w_bitmap = Math.min(width, height);
        Bitmap bitmap = Bitmap.createBitmap(w_bitmap, w_bitmap, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        PaintFlagsDrawFilter paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        canvas.setDrawFilter(paintFlagsDrawFilter);

        draw(canvas, displayName, context, -1, 1);
        return bitmap;
    }

    public static void draw(@NonNull Canvas canvas, String displayName, Context context, int colorBack, float percentText) {
        if (!initColors(context)) {
            return;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        if (width <= 0 || height <= 0) {
            return;
        }
        int w_bitmap = Math.min(width, height);

        float x = w_bitmap / 2f;
        float y = height / 2f;
        int[] color;
        if (colorBack == -1) {
            color = getRandomColor(displayName);
        } else {
            color = new int[]{colorBack, colorBack};
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Shader shader = new LinearGradient(0, 0, 0, height, color, null, Shader.TileMode.REPEAT);
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(shader);
        canvas.drawRect(0, 0, width, height, paint);
        paint.setShader(null);
        if (!TextUtils.isEmpty(displayName)) {
            float textSize = (pattern.matcher(displayName).matches() || displayName.length() == 1) ? w_bitmap / 2.2f : w_bitmap / 2.8f;
            if (percentText > 0) {
                textSize *= percentText;
            }
            int color_text = Color.WHITE;
            paint.setColor(color_text);
            paint.setStyle(Paint.Style.FILL);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD));
            paint.setTextSize(textSize);
            paint.setTextAlign(Paint.Align.CENTER);
            float[] wh = TextMeasure.measure(displayName, textSize);
            float h_text = wh[1];
            float y_text = y + h_text / 2;
            canvas.drawText(displayName, x, y_text, paint);
        }
    }

    private static boolean initColors(Context context) {
        if (colors_start != null && colors_end != null && colors_start.length > 0 && colors_end.length > 0) {
            return true;
        }
        if (context == null) {
            return false;
        }
        colors_start = new int[]{
                ContextCompat.getColor(context, R.color.color_default_face_0_start),
                ContextCompat.getColor(context, R.color.color_default_face_1_start),
                ContextCompat.getColor(context, R.color.color_default_face_2_start),
                ContextCompat.getColor(context, R.color.color_default_face_3_start),
                ContextCompat.getColor(context, R.color.color_default_face_4_start)
        };
        colors_end = new int[]{
                ContextCompat.getColor(context, R.color.color_default_face_0_end),
                ContextCompat.getColor(context, R.color.color_default_face_1_end),
                ContextCompat.getColor(context, R.color.color_default_face_2_end),
                ContextCompat.getColor(context, R.color.color_default_face_3_end),
                ContextCompat.getColor(context, R.color.color_default_face_4_end)
        };
        colors_default = new int[]{
                ContextCompat.getColor(context, R.color.color_main_white_f0f0f0),
                ContextCompat.getColor(context, R.color.color_main_white_e0e0e0)
        };
        return true;
    }

    /**
     * get a random color
     */
    private static int[] getRandomColor(String name) {
        if (!TextUtils.isEmpty(name) && colors_start != null && colors_start.length > 0 && colors_end != null && colors_end.length > 0) {
            int sum = 0;
            int count = name.length();
            for (int i = 0; i < count; i++) {
                sum += name.charAt(i);
            }
            int index = sum % Math.min(colors_start.length, colors_end.length);
            return new int[]{colors_start[index], colors_end[index]};
        }
        return colors_default;
    }
}
