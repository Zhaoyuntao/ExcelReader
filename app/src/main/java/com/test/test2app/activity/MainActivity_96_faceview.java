package com.test.test2app.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.test.test2app.R;
import com.test.test2app.faceview.BlueFaceView;

public class MainActivity_96_faceview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_view);

        BlueFaceView blueFaceView=findViewById(R.id.faceView1);
        blueFaceView.setName("H","",R.drawable.default_face_group);
        blueFaceView.setImageResource(R.drawable.longpic);
    }
}
