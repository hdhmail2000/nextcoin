package com.ifenduo.mahattan_x.entity;

import java.util.List;

public class HelpCenterEntity {
    String title;
    List<String> child;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getChild() {
        return child;
    }

    public void setChild(List<String> child) {
        this.child = child;
    }
}
