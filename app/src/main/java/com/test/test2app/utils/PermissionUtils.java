package com.test.test2app.utils;

import android.content.Context;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

/**
 * created by zhaoyuntao
 * on 2019-11-06
 * description:
 */
public class PermissionUtils {

    public static void requestPermission(final Context context, final RequestResult requestResult, String... permissions) {
        if (context == null || requestResult == null) {
            return;
        }
        AndPermission.with(context)
                .runtime()
                .permission(permissions)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        requestResult.onGranted(permissions);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                            requestResult.onDeniedNotAsk(new PermissionSettings());
                        } else {
                            requestResult.onDenied(permissions);
                        }
                    }
                }).start();
    }

    /**
     * request camera permission
     *
     * @param context
     * @param requestResult
     */
    public static void requestCameraPermission(final Context context, final RequestResult requestResult) {
        requestPermission(context, requestResult, Permission.CAMERA,Permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * request record audio permission
     *
     * @param context
     * @param requestResult
     */
    public static void requestAudioPermission(final Context context, final RequestResult requestResult) {
        requestPermission(context, requestResult, Permission.RECORD_AUDIO);
    }

    /**
     * request write and read file permissions
     *
     * @param context
     * @param requestResult
     */
    public static void requestFilePermission(final Context context, final RequestResult requestResult) {
        requestPermission(context, requestResult, Permission.WRITE_EXTERNAL_STORAGE,Permission.READ_EXTERNAL_STORAGE);
    }
    /**
     * request read phone number permission
     *
     * @param context
     * @param requestResult
     */
    public static void requestPhoneNumberPermission(final Context context, final RequestResult requestResult) {
        requestPermission(context, requestResult, Permission.READ_PHONE_STATE);
    }
    /**
     * request read contacts permission
     *
     * @param context
     * @param requestResult
     */
    public static void requestContactsPermission(final Context context, final RequestResult requestResult) {
        requestPermission(context, requestResult, Permission.READ_CONTACTS);
    }
    /**
     * request location permission
     *
     * @param context
     * @param requestResult
     */
    public static void requestLocationPermission(final Context context, final RequestResult requestResult) {
        requestPermission(context, requestResult, Permission.ACCESS_FINE_LOCATION);
    }
    /**
     * request calendar permission
     *
     * @param context
     * @param requestResult
     */
    public static void requestCalendarPermission(final Context context, final RequestResult requestResult) {
        requestPermission(context, requestResult, Permission.READ_CALENDAR);
    }

    public interface RequestResult {
        void onGranted(List<String> permissions);

        void onDenied(List<String> permissions);

        void onDeniedNotAsk(PermissionSettings permissionSettings);
    }
}
