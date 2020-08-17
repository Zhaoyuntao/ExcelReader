package com.test.test2app.fastrecordviewnew;

import android.app.Application;
import android.content.Context;

import com.test.test2app.A;
import com.test.test2app.utils.BitmapUtils;

/**
 * created by zhaoyuntao
 * on 2020-03-19
 * description:
 */
public class UiUtils {

    public static Application application;

    public static int dipToPx(int i) {
        return BitmapUtils.dip2px(application, i);
    }
}
