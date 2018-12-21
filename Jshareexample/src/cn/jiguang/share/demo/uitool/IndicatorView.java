package cn.jiguang.share.demo.uitool;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cloud on 17/1/13.
 */

class IndicatorView extends View {
    private int mIndicatorWidth;
    private int mIndicatorMargin;
    private int mPageCount;
    private int mSelectPosition;
    private float mLeftPosition;
    private Paint mSelectPaint;
    private Paint mNormalPaint;

    public IndicatorView(Context context) {
        super(context);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(this.measureWidth(widthMeasureSpec), this.measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int desired = this.getPaddingLeft() + this.getPaddingRight() + this.mIndicatorWidth * this.mPageCount * 2 + this.mIndicatorMargin * (this.mPageCount - 1);
        this.mLeftPosition = (float)(this.getMeasuredWidth() - desired) / 2.0F + (float)this.getPaddingLeft();
        int width;
        if(mode == 1073741824) {
            width = Math.max(desired, size);
        } else if(mode == -2147483648) {
            width = Math.min(desired, size);
        } else {
            width = desired;
        }

        return width;
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int height;
        if(mode == 1073741824) {
            height = size;
        } else {
            int desired = this.getPaddingTop() + this.getPaddingBottom() + this.mIndicatorWidth * 2;
            if(mode == -2147483648) {
                height = Math.min(desired, size);
            } else {
                height = desired;
            }
        }

        return height;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(this.mSelectPaint != null && this.mNormalPaint != null) {
            float left = this.mLeftPosition;
            left += (float)this.mIndicatorWidth;

            for(int i = 0; i < this.mPageCount; ++i) {
                canvas.drawCircle(left, (float)this.mIndicatorWidth, (float)this.mIndicatorWidth, i == this.mSelectPosition?this.mSelectPaint:this.mNormalPaint);
                left += (float)(this.mIndicatorMargin + this.mIndicatorWidth * 2);
            }

        }
    }

    public void setSelectedPosition(int position) {
        this.mSelectPosition = position;
        this.invalidate();
    }

    public void setPageCount(int pageCount) {
        this.mPageCount = pageCount;
        this.invalidate();
    }

    public void setIndicator(int size, int margin) {
        this.mIndicatorMargin = this.dip2px((float)margin);
        this.mIndicatorWidth = this.dip2px((float)size);
    }

    public void setIndicatorColor(int normalColor, int selectColor) {
        this.mSelectPaint = new Paint();
        this.mSelectPaint.setStyle(Paint.Style.FILL);
        this.mSelectPaint.setAntiAlias(true);
        this.mSelectPaint.setColor(selectColor);
        this.mNormalPaint = new Paint();
        this.mNormalPaint.setStyle(Paint.Style.FILL);
        this.mNormalPaint.setAntiAlias(true);
        this.mNormalPaint.setColor(normalColor);
    }

    protected int dip2px(float dpValue) {
        float scale = this.getContext().getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }
}