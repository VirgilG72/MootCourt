package cn.jiguang.share.demo.uitool;


/**
 * Created by cloud on 17/1/13.
 */

public final class SnsPlatform implements Comparable{
    public String mKeyword;
    public String mShowWord;
    public String mIcon;
    public String mGrayIcon;
    public int mIndex;
    public String mPlatform;

    public SnsPlatform(String var1) {
        this.mKeyword = var1;
        this.mPlatform = var1;
    }

    public SnsPlatform() {
    }


    @Override
    public String toString() {
        return "SnsPlatform{" +
                "mKeyword='" + mKeyword + '\'' +
                ", mShowWord='" + mShowWord + '\'' +
                ", mIcon='" + mIcon + '\'' +
                ", mGrayIcon='" + mGrayIcon + '\'' +
                ", mIndex=" + mIndex +
                ", mPlatform=" + mPlatform +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if(o==null||!(o instanceof SnsPlatform)){
            return -1;
        }
        return mIndex-((SnsPlatform) o).mIndex;
    }
}
