package com.test.test2app.huaweimeeting;

/**
 * created by zhaoyuntao
 * on 2020-03-17
 * description:
 */
public class ContactEntry {
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_ITEM = 1;
    private String name;
    private int type;

    public ContactEntry(String name, int type) {
        setName(name);
        setType(type);
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }
}
