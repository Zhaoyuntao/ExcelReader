package com.test.test2app.fastrecordview;

import android.content.Context;

import com.test.test2app.utils.BitmapUtils;

/**
 * created by zhaoyuntao
 * on 2020-03-19
 * description:
 */
public class UiUtils {

    public static int dipToPx(Context context, int i) {
        return BitmapUtils.dip2px(context, i);
    }
}
