package com.test.test2app.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * created by zhaoyuntao
 * on 2019-11-08
 * description:
 * Go to application permission setting page
 */
public class PermissionSettings {
    public void goToAppSettings(Context context) {
        if (context != null) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri packageURI = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(packageURI);
            context.startActivity(intent);
        }
    }
}
