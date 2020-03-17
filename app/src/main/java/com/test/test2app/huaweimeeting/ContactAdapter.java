package com.test.test2app.huaweimeeting;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.test.test2app.R;
import com.test.test2app.expandrecyclerview.ExpandableRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * created by zhaoyuntao
 * on 2020-03-16
 * description:
 */
public class ContactAdapter extends ExpandableRecyclerAdapter<ContactParentHolder, ContactEntry> {

    private List<ContactEntry> contactEntries;

    private OnItemClickListener onItemClickListener;

    public ContactAdapter() {
        contactEntries = new ArrayList<>();
    }

    @Override
    public ContactParentHolder getParentViewHolder(@NotNull ViewGroup parent, int viewType) {
        if (viewType == ContactEntry.TYPE_TITLE) {
            return new ContactParentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_huawei_meeting_title_contact_list, parent, false));
        } else {
            return new ContactParentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_huawei_meeting_item_contact_list, parent, false));
        }
    }

    @Override
    protected void onBindExpandableViewHolder(ContactParentHolder holder, int position, ContactEntry contactEntry) {
        holder.setText(contactEntry.getName());
    }

    @Override
    public int getExpandableItemViewType(ContactEntry contactEntry) {
        return contactEntry.getType();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(ContactEntry ContactEntry, int position);
    }

}
