package com.test.test2app.huaweimeeting;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.test2app.utils.BitmapUtils;

/**
 * created by zhaoyuntao
 * on 2020-03-16
 * description:
 */
public class ContactItemDivider extends RecyclerView.ItemDecoration {
    private Paint paint;
    private int dividerHeight;
    private int orientation;

    public ContactItemDivider(@RecyclerView.Orientation int orientation) {
        this.orientation = orientation;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.argb(255, 240, 240, 240));
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        dividerHeight = BitmapUtils.dip2px(parent.getContext(), 5);
        if (orientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, dividerHeight);
        } else {
            outRect.set(0, 0, dividerHeight, 0);
        }
    }

    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        dividerHeight = BitmapUtils.dip2px(parent.getContext(), 5);
        canvas.save();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View childAt = parent.getChildAt(i);
            int left = 0;
            int right = parent.getWidth();
            int top = childAt.getBottom();
            int bottom = childAt.getBottom() + dividerHeight;
            canvas.drawRect(left, top, right, bottom, paint);
        }
        canvas.restore();
    }

}
