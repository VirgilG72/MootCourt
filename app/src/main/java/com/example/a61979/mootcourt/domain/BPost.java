package com.example.a61979.mootcourt.domain;

import cn.bmob.v3.BmobObject;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class BPost extends BmobObject {

    private BUser author;
   private String title;
    private String content;

    public BUser getAuthor() {
        return author;
    }

    public void setAuthor(BUser author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
