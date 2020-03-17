package com.test.test2app.expandrecyclerview;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * created by zhaoyuntao
 * on 2020-03-17
 * description:
 */
public abstract class ExpandableViewHolder extends RecyclerView.ViewHolder {
    private ExpandListener expandListener;

    public ExpandableViewHolder(final View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ExpandableViewHolder.this.onClick(v)) {
                    return;
                }
                changeExpandState(getLayoutPosition());
            }
        });
    }

    final public void initExpandState(int position) {
        if (!isExpandable()) {
            return;
        }
        ExpandableEntry expandableEntry = expandListener.get(position);
        if (expandableEntry == null || !expandableEntry.isParent()) {
            return;
        }
        if (expandableEntry.isExpand()) {
            onExpand();
        } else {
            onShrink();
        }
    }

    final public void changeExpandState(int position) {
        if (!isExpandable()) {
            return;
        }
        ExpandableEntry expandableEntry = expandListener.get(position);
        if (!expandableEntry.isParent()) {
            return;
        }
        if (expandableEntry.isExpand()) {
            onShrink();
            expandListener.shrink(position);
        } else {
            onExpand();
            expandListener.expand(position);
        }
    }

    public boolean onClick(View v) {
        return false;
    }

    protected boolean isExpandable() {
        return true;
    }

    protected void onExpand() {
    }

    protected void onShrink() {
    }


    final public void setExpandListener(ExpandListener expandListener) {
        this.expandListener = expandListener;
    }

    public interface ExpandListener {
        void expand(int position);

        void shrink(int position);

        ExpandableEntry get(int position);
    }
}