package com.test.test2app.huaweimeeting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.test.test2app.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * created by zhaoyuntao
 * on 2020-03-16
 * description:
 */
public class ConferenceAdapter extends RecyclerView.Adapter<ConferenceItemHolder> {
    private List<ConferenceBaseItem> conferenceEntries;

    private OnItemClickListener onItemClickListener;

    public ConferenceAdapter() {
        conferenceEntries = new ArrayList<>();
    }

    @NotNull
    @Override
    public ConferenceItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ConferenceBaseItem.TYPE_ITEM_NOT_START:
            case ConferenceBaseItem.TYPE_ITEM_ONGING:
            case ConferenceBaseItem.TYPE_ITEM_CLOSED:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_huawei_meeting_item_conference_list, parent, false);
                break;
            case ConferenceBaseItem.TYPE_TITLE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_huawei_meeting_item_title_conference_list, parent, false);
                break;
            default:
                view = new View(parent.getContext());
                break;
        }
        return new ConferenceItemHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        ConferenceBaseItem conferenceBaseItem = conferenceEntries.get(position);
        return conferenceBaseItem.getType();
    }

    @Override
    public void onBindViewHolder(@NotNull ConferenceItemHolder holder, final int position) {
        final ConferenceBaseItem conferenceBaseItem = conferenceEntries.get(position);
        if (conferenceBaseItem == null) {
            return;
        }
        //todo set data in ui
        int type = conferenceBaseItem.getType();
        switch (type) {
            case ConferenceBaseItem.TYPE_ITEM_ONGING:
                holder.setTextView1("15:30 2020-03-16");
                holder.setOnButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(conferenceBaseItem, position);
                        }
                    }
                });
                break;
            case ConferenceBaseItem.TYPE_ITEM_CLOSED:
                holder.setTextView1("16:30 2020-03-16");
                holder.hideButton();
                break;
            case ConferenceBaseItem.TYPE_ITEM_NOT_START:
                holder.setTextView1("18:30 2020-03-16");
                holder.hideButton();
                break;
            case ConferenceBaseItem.TYPE_TITLE:
                holder.setTextView1("18:30 2020-03-16");
                holder.setTitle(((TitleEntry) conferenceBaseItem).getTitle());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return conferenceEntries.size();
    }

    public void addConference(ConferenceBaseItem conferenceEntry) {
        conferenceEntries.add(conferenceEntry);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(ConferenceBaseItem conferenceBaseItem, int position);
    }

}
