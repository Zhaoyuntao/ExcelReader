package com.test.test2app.huaweimeeting.contact.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.test.test2app.R;
import com.test.test2app.expandrecyclerview.ExpandableRecyclerAdapter;
import com.test.test2app.huaweimeeting.contact.entry.ContactBaseEntry;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * created by zhaoyuntao
 * on 2020-03-16
 * description:
 */
public class ContactAdapter extends ExpandableRecyclerAdapter<ContactHolder, ContactBaseEntry> {

    private List<ContactBaseEntry> contactEntries;

    private OnItemClickListener onItemClickListener;

    public ContactAdapter() {
        contactEntries = new ArrayList<>();
    }

    @Override
    public ContactHolder onCreateExpandableViewHolder(@NotNull ViewGroup parent, int viewType) {
        if (viewType == ContactBaseEntry.TYPE_TITLE) {
            return new ContactHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_huawei_meeting_title_contact_list, parent, false));
        } else {
            return new ContactHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_huawei_meeting_item_contact_list, parent, false));
        }
    }

    @Override
    protected void onBindExpandableViewHolder(ContactHolder holder, int position, ContactBaseEntry contactEntry) {
        holder.setText(contactEntry.getName());
    }

    @Override
    public int getExpandableItemViewType(ContactBaseEntry contactEntry) {
        return contactEntry.getType();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(ContactBaseEntry ContactBaseEntry, int position);
    }

}
