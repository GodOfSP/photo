package com.fnhelper.photo.beans;

/**
 * Created by little fly on 2018-10-5.
 */

public class MarkListItemBean {

    private String tvTitle;
    private String tvContent;
    private boolean isOpen;
    private String tvMark;
    private int type;

    public MarkListItemBean(String tvTitle, String tvContent, boolean isOpen, String tvMark, int type) {
        this.tvTitle = tvTitle;
        this.tvContent = tvContent;
        this.isOpen = isOpen;
        this.tvMark = tvMark;
        this.type = type;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public String getTvContent() {
        return tvContent;
    }

    public void setTvContent(String tvContent) {
        this.tvContent = tvContent;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getTvMark() {
        return tvMark;
    }

    public void setTvMark(String tvMark) {
        this.tvMark = tvMark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
