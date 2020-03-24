package com.example.a61979.mootcourt.domain.forumbean;

import com.example.a61979.mootcourt.R;

import java.io.Serializable;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class forumBean implements Serializable {
    private int imageID;
    private String title;
    private String viewsCnt;
    private String replyCnt;
    private String username;
    private String content;
    public forumBean(String name,String title,String content) {
        this.username=name;
        this.title=title;
        this.content=content;
       this.imageID= R.drawable.user_logo;
       this.viewsCnt="60";
       this.replyCnt="30";

    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewsCnt() {
        return viewsCnt;
    }

    public void setViewsCnt(String viewsCnt) {
        this.viewsCnt = viewsCnt;
    }

    public String getReplyCnt() {
        return replyCnt;
    }

    public void setReplyCnt(String replyCnt) {
        this.replyCnt = replyCnt;
    }
}
