package com.test.test2app;

import android.content.Context;
import android.content.res.Resources;
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

import com.zhaoyuntao.androidutils.tools.TextMeasure;

import java.util.Random;

/**
 * created by zhaoyuntao
 * on 2019-12-17
 * description:
 */
public class DefaultFaceUtils {

    private static int[] colors_start;
    private static int[] colors_end;

    public static Bitmap getDefaultFace(Context context, int width, int height, String displayName) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        if (!initColors(context)) {
            return bitmap;
        }
        Canvas canvas = new Canvas(bitmap);
        PaintFlagsDrawFilter paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        canvas.setDrawFilter(paintFlagsDrawFilter);

        float x = width / 2f;
        float y = height / 2f;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        float radius = Math.min(width, height) / 2;
        Shader shader = new LinearGradient(0, 0, 0, height, getRandomColor(displayName), null, Shader.TileMode.REPEAT);
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(shader);
        canvas.drawCircle(x, y, radius, paint);
        paint.setShader(null);
        if (!TextUtils.isEmpty(displayName)) {
            float textSize = Math.min(width, height) / 2.2f;
            int color_text = Color.WHITE;
            paint.setColor(color_text);
            paint.setStyle(Paint.Style.FILL);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD));
            paint.setTextSize(textSize);
            paint.setTextAlign(Paint.Align.CENTER);
            float[] wh = TextMeasure.measure(displayName, textSize);
            float w_text = wh[0];
            float h_text = wh[1];
            float x_text = x;// - w_text / 2;
            float y_text = y + h_text / 2;
            canvas.drawText(displayName, x_text, y_text, paint);
        }
        return bitmap;
    }

    private static boolean initColors(Context context) {
        if (colors_start != null && colors_end != null && colors_start.length > 0 && colors_end.length > 0) {
            return true;
        }
        if (context == null) {
            return false;
        }
        Resources resources = context.getResources();
        colors_start = new int[]{
                resources.getColor(R.color.color_default_face_0_start),
                resources.getColor(R.color.color_default_face_1_start),
                resources.getColor(R.color.color_default_face_2_start),
                resources.getColor(R.color.color_default_face_3_start),
                resources.getColor(R.color.color_default_face_4_start)};
        colors_end = new int[]{
                resources.getColor(R.color.color_default_face_0_end),
                resources.getColor(R.color.color_default_face_1_end),
                resources.getColor(R.color.color_default_face_2_end),
                resources.getColor(R.color.color_default_face_3_end),
                resources.getColor(R.color.color_default_face_4_end)};
        return true;
    }

    /**
     * get a random color
     *
     * @param name
     * @return
     */
    private static int[] getRandomColor(String name) {
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
