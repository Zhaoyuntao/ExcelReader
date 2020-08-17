package com.test.test2app.utils;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * created by zhaoyuntao
 * on 2019-10-23
 * description:a vibrator tool
 */
public class VibratorUtil {

    public static void vibrate(Context context) {
        if (context == null) {
            return;
        }
        long milliseconds = 30;
        Vibrator vib = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        if (vib != null) {
            if (Build.VERSION.SDK_INT >= 26) {
                vib.vibrate(VibrationEffect.createOneShot(milliseconds, 5));
            } else {
                vib.vibrate(milliseconds);
            }
        }
    }
}
