package com.hand.weiapi.messagedto.video;

/**
 * Created by xieshuai on 2018/1/18.
 */
public class Video {
    /**
     * 存在的MediaId
     */
    private String MediaId;
    private String Title;
    private String Description;

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

    public String getMediaId() {
        return MediaId;
    }
    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
