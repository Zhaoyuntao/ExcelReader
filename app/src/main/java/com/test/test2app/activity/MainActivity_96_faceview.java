package com.test.test2app.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.test.test2app.BaseActivity;
import com.test.test2app.R;
import com.test.test2app.faceview.BlueFaceView;
import com.test.test2app.faceview.ZLinearLayout;
import com.zhaoyuntao.androidutils.tools.S;
import com.zhaoyuntao.androidutils.tools.T;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity_96_faceview extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_view);

        BlueFaceView blueFaceView = findViewById(R.id.faceView1);
        blueFaceView.setName("H", "", R.drawable.default_face_group);
        blueFaceView.setImageResource(R.drawable.longpic);

        blueFaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.t(activity(), "click blueFaceView");
            }
        });

        blueFaceView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                T.t(activity(), " long click blueFaceView");
                return true;
            }
        });

        ZLinearLayout zLinearLayout = findViewById(R.id.face_container);
        zLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.t(activity(), "click face_container");
            }
        });

        zLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                T.t(activity(), " long click face_container");
                return false;
            }
        });

    }
}
