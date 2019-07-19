package com.sun.snow.module.wxopen.model;

/**
 * @Author: lish
 * @Description:
 * @Date: Create in 2019/7/12
 */
public class ArticleItem {
    private String Title;
    private String Description;
    private String PicUrl;
    private String Url;
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }
    public String getPicUrl() {
        return PicUrl;
    }
    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
    public String getUrl() {
        return Url;
    }
    public void setUrl(String url) {
        Url = url;
    }
}
