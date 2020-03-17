package com.test.test2app.huaweimeeting;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.test.test2app.R;
import com.test.test2app.expandrecyclerview.ExpandableEntry;
import com.test.test2app.expandrecyclerview.ExpandableViewHolder;

/**
 * created by zhaoyuntao
 * on 2020-03-17
 * description:
 */
public class ContactParentHolder extends ExpandableViewHolder {
    private TextView textView;
    private ImageView imageView;

    public ContactParentHolder(final View itemView) {
        super(itemView);
    }

    @Override
    public void initLayout(View itemView) {
        textView = itemView.findViewById(R.id.title);
        imageView = itemView.findViewById(R.id.imageview);
    }

    @Override
    protected void onExpand() {
        if(imageView==null){
            return;
        }
        imageView.setRotation(-180);
    }

    @Override
    protected void onShrink() {
        if(imageView==null){
            return;
        }
        imageView.setRotation(0);
    }

    public void setText(String text) {
        if (textView == null) {
            return;
        }
        textView.setText(text);
    }

}