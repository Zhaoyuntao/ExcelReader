package com.test.test2app;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.test.test2app.fastrecordview.DoubleSwitchView;
import com.test.test2app.fastrecordview.FastRecordView;
import com.test.test2app.fastrecordview.ZImageButton;
import com.zhaoyuntao.androidutils.tools.B;
import com.zhaoyuntao.androidutils.tools.T;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2_record extends BaseActivity {

    DoubleSwitchView changeIconButton;
    FastRecordView fastRecordView;
    ListView listView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        listView = findViewById(R.id.listview);
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("" + i);
        }
        listView.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = new TextView(activity());
                textView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                textView.setText(list.get(position));
                return textView;
            }
        });
        findViewById(R.id.testclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fastRecordView.stopRecord();
                changeIconButton.nextIndex();
            }
        });

        fastRecordView = findViewById(R.id.fastrecord);
        fastRecordView.setAutoUpdateDuration(true);

        changeIconButton = findViewById(R.id.button);
        changeIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.t(activity(), "click");
            }
        });

        ImageButton imageButton1 = new ImageButton(this);
        ZImageButton imageButton2 = new ZImageButton(this);

        imageButton1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageButton2.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageButton1.setImageResource(R.drawable.ic_messages_delete);
        imageButton2.setImageResource(R.drawable.ic_voice_message_record);

        int padding = B.dip2px(this, 5);

        imageButton1.setPaddingRelative(padding, padding, padding, padding);
        imageButton2.setPaddingRelative(padding, padding, padding, padding);

//        changeIconButton.setDefaultView(imageButton1);
//        changeIconButton.setSecondView(imageButton2);

        imageButton1.setBackground(null);
        imageButton2.setBackground(null);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.t(MainActivity2_record.this,"delete click");
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.t(MainActivity2_record.this,"record click");
            }
        });

        imageButton1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                T.t(MainActivity2_record.this,"delete long click");
                return true;
            }
        });
        imageButton2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                T.t(MainActivity2_record.this,"record long click");
                return false;
            }
        });

        imageButton2.setTouchConnection(fastRecordView);

        fastRecordView.setCallBack(new FastRecordView.CallBack() {
            @Override
            public void whenStartRecord() {
                T.t(activity(), "started");
            }

            @Override
            public void whenStopRecord(boolean needSend) {
                T.t(activity(), "stopped");
            }

            @Override
            public void whenCancelRecord() {
                T.t(activity(), "canceled");
            }

            @Override
            public void whenSendClick() {
                T.t(activity(), "sent");
            }

            @Override
            public void whenActionDown() {

            }

            @Override
            public void whenActionUp() {

            }

            @Override
            public void whenAbandonedVoice() {
                T.t(activity(), "abandoned");
            }
        });
    }

}
