package com.test.test2app.activity;

import android.os.Bundle;
import android.view.View;

import com.test.test2app.BaseActivity;
import com.test.test2app.R;
import com.test.test2app.stickerreply.StickerRepliedBean;
import com.test.test2app.stickerreply.StickerRepliedParticipantItemBean;
import com.test.test2app.stickerreply.StickerReplyView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_98_sticker_reply extends BaseActivity {

    StickerReplyView stickerReplyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sticker_reply);

        stickerReplyView = findViewById(R.id.view);


        findViewById(R.id.button_init).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });
        findViewById(R.id.button_add1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add("\uD83D\uDE0A");
            }
        });
        findViewById(R.id.button_add2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add("\uD83D\uDE04");
            }
        });
        findViewById(R.id.button_add3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add("\uD83D\uDC4C");
            }
        });
        findViewById(R.id.button_add4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add("\uD83D\uDC36");
            }
        });
        findViewById(R.id.button_add5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add("⬆️");
            }
        });

        init();

    }

    private void add(String sticker) {
        StickerRepliedParticipantItemBean stickerRepliedParticipantItemBean = new StickerRepliedParticipantItemBean();
        stickerRepliedParticipantItemBean.setSticker(sticker);
        stickerRepliedParticipantItemBean.setName("abcd");
        stickerRepliedParticipantItemBean.setUid("abcd");
        stickerRepliedParticipantItemBean.setTime(System.currentTimeMillis());
        stickerReplyView.addSticker(stickerRepliedParticipantItemBean);
    }

    private String[] arr = {"⬆️", "🈚️", "😄", "😯", "🐸"};

    private void init() {
        List<StickerRepliedBean> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            StickerRepliedBean stickerRepliedBean = new StickerRepliedBean();
            stickerRepliedBean.setSticker(arr[i]);
            List<StickerRepliedParticipantItemBean> list1 = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                StickerRepliedParticipantItemBean stickerRepliedParticipantItemBean = new StickerRepliedParticipantItemBean();
                if (j > 0) {
                    stickerRepliedParticipantItemBean.setUid("abcd" + j);
                } else {
                    stickerRepliedParticipantItemBean.setUid("abcd");
                }
                stickerRepliedParticipantItemBean.setName("abcd");
                stickerRepliedParticipantItemBean.setTime(123445);
                stickerRepliedParticipantItemBean.setSticker(arr[i]);
                list1.add(stickerRepliedParticipantItemBean);
            }
            stickerRepliedBean.setStickerRepliedParticipantItemBeans(list1);
            stickerRepliedBean.setTime(i);
            list.add(stickerRepliedBean);
        }

        stickerReplyView.initSticker(list);
    }
}