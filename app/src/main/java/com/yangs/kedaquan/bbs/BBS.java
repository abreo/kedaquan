package com.yangs.kedaquan.bbs;

import android.graphics.Color;

import com.yangs.kedaquan.R;

/**
 * Created by yangs on 2017/8/2.
 */

public class BBS {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private String title;
    private String user;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    private String num;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private int color = R.color.bbs_listview_gray2;
}
