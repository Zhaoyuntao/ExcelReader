package com.test.test2app;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.test.test2app.huaweimeeting.conference.entry.ConferenceBaseItem;
import com.test.test2app.huaweimeeting.conference.adapter.ConferenceAdapter;
import com.test.test2app.huaweimeeting.conference.entry.ConferenceEntry;
import com.test.test2app.expandrecyclerview.ExpandableEntry;
import com.test.test2app.huaweimeeting.conference.entry.DividerEntry;
import com.test.test2app.huaweimeeting.contact.adapter.ContactAdapter;
import com.test.test2app.huaweimeeting.contact.entry.ContactBaseEntry;
import com.test.test2app.huaweimeeting.contact.entry.ContactEntry;
import com.test.test2app.expandrecyclerview.ItemDivider;
import com.test.test2app.huaweimeeting.contact.entry.ContactTitleEntry;
import com.test.test2app.huaweimeeting.contact.entry.DeviceEntry;
import com.test.test2app.pagerview.MainTabPagerAdapter;
import com.test.test2app.huaweimeeting.conference.entry.ConferenceTitleEntry;
import com.test.test2app.pagerview.ZPagerView;
import com.test.test2app.pagerview.ZTabLayout;
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
        List<ExpandableEntry> conferenceAdapterData = new ArrayList<>();
        //on going
        ExpandableEntry<ConferenceBaseItem> conferenceOnGoing = new ExpandableEntry<>(new ConferenceTitleEntry("ONGOING"));
        conferenceOnGoing.addParamEntry(new ConferenceEntry(ConferenceBaseItem.TYPE_ITEM_ONGOING));
        conferenceOnGoing.addParamEntry(new ConferenceEntry(ConferenceBaseItem.TYPE_ITEM_ONGOING));
        conferenceOnGoing.setExpand(true);
        conferenceAdapterData.add(conferenceOnGoing);
        //divider
        ExpandableEntry<ConferenceBaseItem> divider = new ExpandableEntry<>(new DividerEntry());
        conferenceAdapterData.add(divider);
        //schedule
        ExpandableEntry<ConferenceBaseItem> conferenceSchedule = new ExpandableEntry<>(new ConferenceTitleEntry("SCHEDULED"));
        conferenceSchedule.addParamEntry(new ConferenceEntry(ConferenceBaseItem.TYPE_ITEM_NOT_START));
        conferenceSchedule.addParamEntry(new ConferenceEntry(ConferenceBaseItem.TYPE_ITEM_NOT_START));
        conferenceSchedule.setExpand(true);
        conferenceAdapterData.add(conferenceSchedule);
        conferenceAdapterData.add(divider);
        //closed
        ExpandableEntry<ConferenceBaseItem> conferenceClosed = new ExpandableEntry<>(new ConferenceTitleEntry("CLOSED"));
        conferenceClosed.addParamEntry(new ConferenceEntry(ConferenceBaseItem.TYPE_ITEM_CLOSED));
        conferenceClosed.addParamEntry(new ConferenceEntry(ConferenceBaseItem.TYPE_ITEM_CLOSED));
        conferenceClosed.setExpand(true);
        conferenceAdapter.setOnItemClickListener(new ConferenceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ConferenceBaseItem conferenceBaseItem, int position) {
                T.t(MainActivity92_huawei_meeting.this, "conferenceBaseItem:" + conferenceBaseItem.getType() + " position:" + position);
            }
        });
        conferenceAdapterData.add(conferenceClosed);
        conferenceAdapter.addData(conferenceAdapterData);


        final ContactAdapter expandableRecyclerAdapter = new ContactAdapter();
        List<ExpandableEntry> list = new ArrayList<>();
        ExpandableEntry<ContactBaseEntry> totokContacts = new ExpandableEntry<>(new ContactTitleEntry("TOTOK CONTACTS"));
        totokContacts.addParamEntry(new ContactEntry("123"));
        totokContacts.addParamEntry(new ContactEntry("456"));
        totokContacts.addParamEntry(new ContactEntry("789"));
        totokContacts.setExpand(true);
        list.add(totokContacts);

        ExpandableEntry<ContactBaseEntry> deviceList = new ExpandableEntry<>(new ContactTitleEntry("DEVICE LIST"));
        deviceList.addParamEntry(new DeviceEntry("abc"));
        deviceList.addParamEntry(new DeviceEntry("def"));
        deviceList.addParamEntry(new DeviceEntry("ghi"));
        deviceList.setExpand(false);
        list.add(deviceList);
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
