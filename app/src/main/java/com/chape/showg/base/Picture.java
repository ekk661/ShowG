package com.chape.showg.base;

/**
 * Created by Administrator on 2017/8/22.
 */

public class Picture {
    String content_url;
    String pic_url;
    public Picture(String content_url,String pic_url) {
        this.content_url = content_url;
        this.pic_url=pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getPic_url() {
        return pic_url;
    }

    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }
}
