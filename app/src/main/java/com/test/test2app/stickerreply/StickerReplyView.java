package com.test.test2app.stickerreply;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * created by zhaoyuntao
 * on 28/12/2020
 * description:
 */
public class StickerReplyView extends RecyclerView {

    private StickerReplyAdapter stickerReplyAdapter;

    public StickerReplyView(@NonNull Context context) {
        super(context);
        init();
    }

    public StickerReplyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StickerReplyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        StickerReplyLayoutManager stickerReplyLayoutManager = new StickerReplyLayoutManager();
        addItemDecoration(new StickerReplyItemDecoration());
        setLayoutManager(stickerReplyLayoutManager);

        stickerReplyAdapter = new StickerReplyAdapter();
        setAdapter(stickerReplyAdapter);
    }

    public void initSticker(List<StickerRepliedBean> stickerRepliedBean) {
        stickerReplyAdapter.initData(stickerRepliedBean);
    }

    public void addSticker(StickerRepliedParticipantItemBean stickerRepliedParticipantItemBean) {
        stickerReplyAdapter.addSticker(stickerRepliedParticipantItemBean);
    }

    public void removeSticker(StickerRepliedParticipantItemBean stickerRepliedParticipantItemBean) {
        stickerReplyAdapter.removeSticker(stickerRepliedParticipantItemBean);
    }
}
