package com.example.a61979.mootcourt.domain;

import cn.bmob.v3.BmobObject;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class BComment extends BmobObject {
    private  String content;
    private  BUser author;
    private BPost post;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BUser getAuthor() {
        return author;
    }

    public void setAuthor(BUser author) {
        this.author = author;
    }

    public BPost getPost() {
        return post;
    }

    public void setPost(BPost post) {
        this.post = post;
    }
}
