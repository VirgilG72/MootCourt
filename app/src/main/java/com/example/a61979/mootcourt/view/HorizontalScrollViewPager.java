package com.example.a61979.mootcourt.view;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}水平方向滑动的viewpager
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class HorizontalScrollViewPager extends ViewPager {

    private float startX;
    private float startY;
    private float endX;
    private float endY;

    public HorizontalScrollViewPager(Context context) {
        super(context);
    }

    public HorizontalScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //请求父层视图不拦截，当前控件的事件
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);//都把事件传给当前控件
                //1、记录起始坐标
                startX = ev.getX();
                startY = ev.getY();
                break;

            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_MOVE:
                //2、来到新的坐标
                endX = ev.getX();
                endY = ev.getY();
                //3、计算偏移量
                float distanceX = endX - startX;
                float distanceY = endY - startY;
                //4、判断滑动方向
                if (Math.abs(distanceX)>Math.abs(distanceY)){
                    //水平方向滑动
                    //2.1当滑动到ViewPager的第0个页面，且手指从左到右
                    if (getCurrentItem()==0&&distanceX>0){
                        getParent().requestDisallowInterceptTouchEvent(false);//父类拦截，子类无法处理
                    }
                    //2.2当滑动到ViewPager的最后一个页面时，且手指从右到左
                    else if ((getCurrentItem()==(getAdapter().getCount()-1))&&distanceX<0){
                        getParent().requestDisallowInterceptTouchEvent(false);//父类拦截，子类无法处理

                    }
                    else{
                        getParent().requestDisallowInterceptTouchEvent(true);//父类不拦截，子类自行处理
                    }
                }
                else {
                    //竖直方向滑动
                    getParent().requestDisallowInterceptTouchEvent(false);//父类拦截，子类无法处理
                }
                break;

            default:
                break;

        }
        return super.dispatchTouchEvent(ev);
    }
}
