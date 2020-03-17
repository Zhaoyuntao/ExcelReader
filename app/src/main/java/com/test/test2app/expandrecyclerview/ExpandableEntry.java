package com.test.test2app.expandrecyclerview;

import java.util.ArrayList;
import java.util.List;


/**
 * created by zhaoyuntao
 * on 2020-03-16
 * description:
 */
public class ExpandableEntry<A> {
    private boolean expand;
    private boolean isParent = true;
    private int position;
    private List<ExpandableEntry> list = new ArrayList<>();

    private A a;

    public<B extends A> ExpandableEntry(B b) {
        this.a = b;
    }

    public boolean isParent() {
        return isParent;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int size() {
        return list.size();
    }

    public List<ExpandableEntry> getAll() {
        List<ExpandableEntry> list = new ArrayList<>();
        list.add(this);
        if (expand) {
            list.addAll(this.list);
        }
        return list;
    }

    public List<ExpandableEntry> getAllChild() {
        if (expand) {
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    public ExpandableEntry getChild(int i) {
        return list.get(i);
    }

    public A getParamEntry() {
        return a;
    }

    public <B> void addParamEntry(B a) {
        ExpandableEntry<B> expandableEntry = new ExpandableEntry<>(a);
        list.add(expandableEntry);
        this.isParent = true;
        expandableEntry.isParent = false;
    }
}
