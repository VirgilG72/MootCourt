package cn.jiguang.share.demo.uitool;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by cloud on 17/1/13.
 */

class JGActionFrame extends LinearLayout {
    private ShareBoardConfig mConfig;
    private PopupWindow.OnDismissListener mDismissListener;

    public JGActionFrame(Context context) {
        super(context);
    }

    public JGActionFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(11)
    public JGActionFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public JGActionFrame(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setSnsPlatformData(List<SnsPlatform> platforms) {
        ShareBoardConfig defaultConfig = new ShareBoardConfig();
        this.setSnsPlatformData(platforms, defaultConfig);
    }

    public void setSnsPlatformData(List<SnsPlatform> platforms, ShareBoardConfig config) {
        if(config == null) {
            this.mConfig = new ShareBoardConfig();
        } else {
            this.mConfig = config;
        }

        this.init(platforms);
    }

    private void init(List<SnsPlatform> platforms) {
        this.setBackgroundColor(Color.argb(50, 0, 0, 0));
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0F, 1.0F);
        alphaAnimation.setDuration(100L);
        this.setAnimation(alphaAnimation);
        this.setOrientation(LinearLayout.VERTICAL);
        if(this.mConfig.mShareboardPosition == ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM) {
            this.setGravity(80);
        } else if(this.mConfig.mShareboardPosition == ShareBoardConfig.SHAREBOARD_POSITION_CENTER) {
            this.setGravity(17);
            int shareMenuLayout = this.dip2px(36.0F);
            this.setPadding(shareMenuLayout, 0, shareMenuLayout, 0);
        }

        this.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(JGActionFrame.this.mDismissListener != null) {
                    JGActionFrame.this.mDismissListener.onDismiss();
                }

            }
        });
        View shareMenuLayout1 = this.createShareboardLayout(platforms);
        if(shareMenuLayout1 != null) {
            shareMenuLayout1.setClickable(true);
            this.addView(shareMenuLayout1);
        }
    }

    private View createShareboardLayout(List<SnsPlatform> platforms) {
        LinearLayout shareMenuLayout = new LinearLayout(this.getContext());
        shareMenuLayout.setBackgroundColor(this.mConfig.mShareboardBgColor);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(this.mConfig.mShareboardPosition == ShareBoardConfig.SHAREBOARD_POSITION_CENTER && this.mConfig.mTopMargin != 0) {
            layoutParams.topMargin = this.mConfig.mTopMargin;
        }

        shareMenuLayout.setOrientation(LinearLayout.VERTICAL);
        shareMenuLayout.setLayoutParams(layoutParams);
        if(this.mConfig.mTitleVisibility) {
            View pageHeight = this.createShareTitle();
            shareMenuLayout.addView(pageHeight);
        }

        int pageHeight1 = this.mConfig.calculateMenuHeightInDp(platforms.size());
        ViewPager viewPager = this.createViewPagerInstance();
        if(viewPager != null) {
            SocializeMenuPagerAdapter pagerAdapter = new SocializeMenuPagerAdapter(this.getContext(), this.mConfig);
            pagerAdapter.setData(platforms);
            this.settingMenuLayout(viewPager, pageHeight1);
            shareMenuLayout.addView(viewPager);
            viewPager.setAdapter(pagerAdapter);
            final IndicatorView indicatorView = this.mConfig.mIndicatorVisibility?this.createIndicatorView():null;
            if(indicatorView != null) {
                indicatorView.setPageCount(pagerAdapter.getCount());
                shareMenuLayout.addView(indicatorView);
                indicatorView.setVisibility(VISIBLE);
            }

            ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                public void onPageSelected(int position) {
                    if(indicatorView != null) {
                        indicatorView.setSelectedPosition(position);
                    }

                }

                public void onPageScrollStateChanged(int state) {
                }
            };
            if(this.verifyMethodExists()) {
                viewPager.addOnPageChangeListener(pageChangeListener);
            } else {
                viewPager.setOnPageChangeListener(pageChangeListener);
            }
        } else {
            Log.e("UMActionFrame","viewpager is null");
        }

        if(this.mConfig.mCancelBtnVisibility) {
            View cancelBtn = this.createCancelBtn();
            shareMenuLayout.addView(cancelBtn);
        }

        return shareMenuLayout;
    }

    private View createShareTitle() {
        TextView title = new TextView(this.getContext());
        title.setText(this.mConfig.mTitleText);
        title.setTextColor(this.mConfig.mTitleTextColor);
        title.setTextSize(16.0F);
        title.setGravity(17);
        title.setMaxLines(1);
        title.setEllipsize(TextUtils.TruncateAt.END);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = this.dip2px(20.0F);
        title.setLayoutParams(layoutParams);
        return title;
    }

    private void settingMenuLayout(View viewPager, int pageHeight) {
        int padding = this.dip2px(20.0F);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, this.dip2px((float)pageHeight));
        layoutParams.topMargin = padding;
        layoutParams.leftMargin = layoutParams.rightMargin = this.dip2px(10.0F);
        viewPager.setLayoutParams(layoutParams);
        viewPager.setPadding(0, 0, 0, padding);
    }

    public IndicatorView createIndicatorView() {
        int padding = this.dip2px(20.0F);
        IndicatorView indicatorView = new IndicatorView(this.getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = padding;
        indicatorView.setLayoutParams(layoutParams);
        indicatorView.setIndicatorColor(this.mConfig.mIndicatorNormalColor, this.mConfig.mIndicatorSelectedColor);
        indicatorView.setIndicator(3, 5);
        return indicatorView;
    }

    public View createCancelBtn() {
        TextView cancelBtn = new TextView(this.getContext());
        cancelBtn.setText(this.mConfig.mCancelBtnText);
        cancelBtn.setTextColor(this.mConfig.mCancelBtnColor);
        cancelBtn.setClickable(true);
        cancelBtn.setTextSize(15.0F);
        cancelBtn.setGravity(17);
        if(this.mConfig.mCancelBtnBgPressedColor != 0) {
            if(Build.VERSION.SDK_INT >= 16) {
                cancelBtn.setBackground(this.getBtnBg());
            } else {
                cancelBtn.setBackgroundDrawable(this.getBtnBg());
            }
        } else {
            cancelBtn.setBackgroundColor(this.mConfig.mCancelBtnBgColor);
        }

        cancelBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(JGActionFrame.this.mDismissListener != null) {
                    JGActionFrame.this.mDismissListener.onDismiss();
                }

            }
        });
        int height = this.dip2px(50.0F);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        layoutParams.bottomMargin = this.dip2px(20.0F);
        cancelBtn.setLayoutParams(layoutParams);
        return cancelBtn;
    }

    private StateListDrawable getBtnBg() {
        ColorDrawable normalColor = new ColorDrawable(this.mConfig.mCancelBtnBgColor);
        ColorDrawable pressedColor = new ColorDrawable(this.mConfig.mCancelBtnBgPressedColor);
        StateListDrawable bg = new StateListDrawable();
        int pressed = 16842919;
        bg.addState(new int[]{pressed}, pressedColor);
        bg.addState(new int[0], normalColor);
        return bg;
    }

    private int dip2px(float dpValue) {
        float scale = this.getContext().getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

    void setDismissListener(PopupWindow.OnDismissListener dismissListener) {
        this.mDismissListener = dismissListener;
    }

    private ViewPager createViewPagerInstance() {
        ViewPager viewPager = null;

        try {
            Class e = Class.forName("android.support.v4.view.ViewPager");
            Class[] parTypes = new Class[]{Context.class};
            Constructor constructor = e.getConstructor(parTypes);
            Object[] pars = new Object[]{this.getContext()};
            viewPager = (ViewPager)constructor.newInstance(pars);
            return viewPager;
        } catch (Exception var6) {
            Log.e("JGActionFrame","JGActionFrame create ViewPager Instance error:" + var6);
            return viewPager;
        }
    }

    private boolean verifyMethodExists() {
        try {
            Class e = Class.forName("android.support.v4.view.ViewPager");
            Method method = e.getMethod("addOnPageChangeListener", new Class[]{ViewPager.OnPageChangeListener.class});
            if(method != null) {
                return true;
            }
        } catch (Exception var3) {
            Log.e("JGActionFrame","JGActionFrame verifyMethodExists addOnPageChangeListener error:" + var3);
        }

        return false;
    }

}
