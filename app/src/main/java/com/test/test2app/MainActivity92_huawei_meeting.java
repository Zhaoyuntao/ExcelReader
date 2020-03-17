package com.test.test2app;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.test.test2app.huaweimeeting.ConferenceBaseItem;
import com.test.test2app.huaweimeeting.ConferenceAdapter;
import com.test.test2app.huaweimeeting.ConferenceEntry;
import com.test.test2app.expandrecyclerview.ExpandableEntry;
import com.test.test2app.huaweimeeting.ContactAdapter;
import com.test.test2app.huaweimeeting.ContactEntry;
import com.test.test2app.huaweimeeting.ItemDivider;
import com.test.test2app.huaweimeeting.MainTabPagerAdapter;
import com.test.test2app.huaweimeeting.TitleEntry;
import com.test.test2app.huaweimeeting.ZPagerView;
import com.test.test2app.huaweimeeting.ZTabLayout;
import com.zhaoyuntao.androidutils.tools.T;

import java.util.ArrayList;
import java.util.List;

public class MainActivity92_huawei_meeting extends BaseActivity {

    private TextView textView;
    private ZPagerView viewPager;
    private ZTabLayout zTabLayout;
    private String[] test = {"Conference List", "Contacts"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity92_huawei_meeting);

        textView = findViewById(R.id.msg);
        zTabLayout = findViewById(R.id.ztab);
        viewPager = findViewById(R.id.viewpager);

        final ConferenceAdapter conferenceAdapter = new ConferenceAdapter();
        conferenceAdapter.addConference(new TitleEntry("ONGOING"));
        conferenceAdapter.addConference(new ConferenceEntry(ConferenceBaseItem.TYPE_ITEM_ONGING));
        conferenceAdapter.addConference(new ConferenceEntry(ConferenceBaseItem.TYPE_ITEM_ONGING));
        conferenceAdapter.addConference(new TitleEntry("SCHEDULED"));
        conferenceAdapter.addConference(new ConferenceEntry(ConferenceBaseItem.TYPE_ITEM_NOT_START));
        conferenceAdapter.addConference(new TitleEntry("CLOSED"));
        conferenceAdapter.addConference(new ConferenceEntry(ConferenceBaseItem.TYPE_ITEM_CLOSED));
        conferenceAdapter.setOnItemClickListener(new ConferenceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ConferenceBaseItem conferenceBaseItem, int position) {
                T.t(MainActivity92_huawei_meeting.this, "conferenceBaseItem:" + conferenceBaseItem.getType() + " position:" + position);
            }
        });


        final ContactAdapter expandableRecyclerAdapter = new ContactAdapter();
        List<ExpandableEntry> list = new ArrayList<>();
        ExpandableEntry expandableEntry = new ExpandableEntry<>(new ContactEntry("TOTOK CONTACTS", ContactEntry.TYPE_TITLE));
        expandableEntry.add(new ExpandableEntry<>(new ContactEntry("123", ContactEntry.TYPE_ITEM)));
        expandableEntry.add(new ExpandableEntry<>(new ContactEntry("456", ContactEntry.TYPE_ITEM)));
        expandableEntry.add(new ExpandableEntry<>(new ContactEntry("789", ContactEntry.TYPE_ITEM)));
        expandableEntry.setExpand(true);
        list.add(expandableEntry);

        ExpandableEntry expandableEntry2 = new ExpandableEntry<>(new ContactEntry("DEVICE LIST", ContactEntry.TYPE_TITLE));
        expandableEntry2.add(new ExpandableEntry<>(new ContactEntry("abc", ContactEntry.TYPE_ITEM)));
        expandableEntry2.add(new ExpandableEntry<>(new ContactEntry("def", ContactEntry.TYPE_ITEM)));
        expandableEntry2.add(new ExpandableEntry<>(new ContactEntry("ghi", ContactEntry.TYPE_ITEM)));
        expandableEntry2.setExpand(false);
        list.add(expandableEntry2);
        expandableRecyclerAdapter.addData(list);

        MainTabPagerAdapter mainTabPagerAdapter = new MainTabPagerAdapter(new MainTabPagerAdapter.PagerInflater() {
            @Override
            public int getCount() {
                return test.length;
            }

            @Override
            public View getPageLayout(int position) {
                if (position == 0) {
                    View view = LayoutInflater.from(MainActivity92_huawei_meeting.this).inflate(R.layout.layout_huawei_meeting_page_conference_list, null);
                    RecyclerView recyclerView = view.findViewById(R.id.conferences);
                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                    recyclerView.addItemDecoration(new ItemDivider(RecyclerView.VERTICAL));
                    recyclerView.setAdapter(conferenceAdapter);
                    return view;
                } else {
                    View view = LayoutInflater.from(MainActivity92_huawei_meeting.this).inflate(R.layout.layout_huawei_meeting_page_conference_list, null);
                    RecyclerView recyclerView = view.findViewById(R.id.conferences);
                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                    recyclerView.setAdapter(expandableRecyclerAdapter);
                    return view;
                }
            }
        });
        viewPager.setAdapter(mainTabPagerAdapter);
        viewPager.addOnScrollListener(new ZPagerView.OnScrollListener() {
            @Override
            public void onScrollChange(ViewPager v, int scrollX, int scrollY, int scrollXOld, int scrollYOld) {
                textView.setText("x:" + scrollX + " w:" + v.getWidth());
            }
        });
        zTabLayout.connect(viewPager);

        for (String content : test) {
            ZTabLayout.Item item = new ZTabLayout.Item();
            item.setText(content);
            item.setTextSizeDp(16);
            item.setTypeface(Typeface.DEFAULT_BOLD);
            zTabLayout.addItem(item);
        }
    }

}
