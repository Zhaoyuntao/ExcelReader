package com.test.test2app.appbase;

import android.app.Application;

import com.test.test2app.fastrecordviewnew.UiUtils;
import com.test.test2app.threadpool.Utilities;

/**
 * created by zhaoyuntao
 * on 2020-03-31
 * description:
 */
public class ZApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utilities.setContext(this.getApplicationContext());
        UiUtils.application = this;
    }
}
