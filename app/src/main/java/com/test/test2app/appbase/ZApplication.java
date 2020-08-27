package com.test.test2app.appbase;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.core.provider.FontRequest;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.text.FontRequestEmojiCompatConfig;

import com.test.test2app.R;
import com.test.test2app.fastrecordviewnew.UiUtils;
import com.test.test2app.threadpool.Utilities;
import com.zhaoyuntao.androidutils.tools.S;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

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
        initEmojiCompat();
    }
    private void initEmojiCompat() {
        final EmojiCompat.Config config;
        // Use a downloadable font for EmojiCompat
        final FontRequest fontRequest = new FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                "Noto Color Emoji Compat",
                R.array.com_google_android_gms_fonts_certs);
        config = new FontRequestEmojiCompatConfig(getApplicationContext(), fontRequest)
                .setReplaceAll(true)
                .registerInitCallback(new EmojiCompat.InitCallback() {
                    @Override
                    public void onInitialized() {
                        S.s("EmojiCompat initialized");
                    }

                    @Override
                    public void onFailed(@Nullable Throwable throwable) {
                        S.e("EmojiCompat initialization failed", throwable);
                    }
                });

        EmojiCompat.init(config);
    }
}
