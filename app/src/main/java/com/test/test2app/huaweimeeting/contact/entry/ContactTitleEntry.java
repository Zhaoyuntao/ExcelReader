package com.test.test2app.huaweimeeting.contact.entry;

/**
 * created by zhaoyuntao
 * on 2020-03-17
 * description:
 */
public class ContactTitleEntry extends ContactBaseEntry {

    public ContactTitleEntry(String name) {
        super(name);
    }

    public int getType() {
        return TYPE_TITLE;
    }
}
