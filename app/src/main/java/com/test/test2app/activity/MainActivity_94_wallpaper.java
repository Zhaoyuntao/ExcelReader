package com.test.test2app.activity;

import android.os.Bundle;

import com.test.test2app.BaseActivity;
import com.test.test2app.R;
import com.test.test2app.wallpaper.AdapterImageView;
import com.zhaoyuntao.androidutils.tools.S;

import java.io.File;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifDrawableBuilder;

public class MainActivity_94_wallpaper extends BaseActivity {

    private AdapterImageView adapterImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_94_wallpaper);

        adapterImageView=findViewById(R.id.wall);

        GifDrawable gifDrawable=null;
        GifDrawableBuilder builder = new GifDrawableBuilder();

        try {
            gifDrawable = builder.from(getResources(),R.raw.bbbb).setRenderingTriggeredOnDraw(true).build();
            adapterImageView.setImageDrawable(gifDrawable);
        } catch (Exception e) {
            e.printStackTrace();
            S.e(e);
        }
    }
}
