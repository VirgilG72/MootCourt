package com.example.a61979.mootcourt.listener;

import android.app.Activity;
import androidx.annotation.IdRes;
import android.widget.RadioGroup;

import com.example.a61979.mootcourt.R;
import com.example.a61979.mootcourt.activity.MainActivity;
import com.example.a61979.mootcourt.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
    private final Activity context;
    private final NoScrollViewPager viewpager;

    public MyOnCheckedChangeListener(NoScrollViewPager viewpager, Activity context){
        this.viewpager=viewpager;
        this.context=context;

    }
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch(checkedId){
            case R.id.rb_home:
                viewpager.setCurrentItem(0);
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                break;

            case R.id.rb_discover:
                viewpager.setCurrentItem(1);
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);

                break;

            case R.id.rb_signup:
                viewpager.setCurrentItem(2);
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                break;

            case R.id.rb_view:
                viewpager.setCurrentItem(3);
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                break;

            case R.id.rb_user:
                viewpager.setCurrentItem(4);
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                break;

        }
    }
    /**
     * 根据传入的参数设置是否让slidingmenu可以滑动
     * @param touchmodeFullscreen
     */
    private   void isEnableSlidingMenu(int touchmodeFullscreen) {
        MainActivity mainActivity=(MainActivity) context;
        mainActivity.getSlidingMenu().setTouchModeAbove(touchmodeFullscreen);
    }

}
