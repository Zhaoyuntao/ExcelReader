package com.test.test2app.huaweimeeting;


/**
 * created by zhaoyuntao
 * on 2020-03-16
 * description:
 */
public class TitleEntry extends ConferenceBaseItem {

    private String title;

    public TitleEntry(String title) {
        setTitle(title);
    }

    @Override
    public int getType() {
        return TYPE_TITLE;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
