package com.example.a61979.mootcourt.domain;

import cn.bmob.v3.BmobObject;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class BReply extends BmobObject {
    private BUser author;
    private BComment comment;
    private String content;
    public BReply(String content,BUser author){
        this.content=content;
        this.author=author;
    }
    public BUser getAuthor() {
        return author;
    }

    public void setAuthor(BUser author) {
        this.author = author;
    }

    public BComment getComment() {
        return comment;
    }

    public void setComment(BComment comment) {
        this.comment = comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
