package cn.jiguang.share.demo.uitool;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * Created by cloud on 17/1/13.
 */

public class SocializeImageView extends ImageButton {
    private boolean mIsSelected;
    private int mNormalColor;
    private int mPressedColor;
    private int mIconPressedColor;
    private boolean mIsPressEffect;
    private int mBgShape;
    public static int BG_SHAPE_NONE = 0;
    public static int BG_SHAPE_CIRCULAR = 1;
    public static int BG_SHAPE_ROUNDED_SQUARE = 2;
    protected Paint mNormalPaint;
    protected Paint mPressedPaint;
    private RectF mSquareRect;
    private int mAngle;
    private int mStrokeWidth;
    private int mStrokeColor;
    private Paint mStrokePaint;

    public SocializeImageView(Context context) {
        super(context);
        this.init();
    }

    public SocializeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public SocializeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    @TargetApi(21)
    public SocializeImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init();
    }

    private void init() {
        if(Build.VERSION.SDK_INT < 16) {
            this.setBackgroundDrawable((Drawable)null);
        } else {
            this.setBackground((Drawable)null);
        }

        this.setClickable(false);
        this.setScaleType(ScaleType.CENTER_INSIDE);
    }

    public void setBackgroundShape(int shapeType) {
        this.setBackgroundShape(shapeType, 0);
    }

    public void setBackgroundShape(int shapeType, int angle) {
        this.mBgShape = shapeType;
        if(shapeType != BG_SHAPE_ROUNDED_SQUARE) {
            this.mAngle = 0;
        } else {
            float density = this.getResources().getDisplayMetrics().density;
            this.mAngle = (int)((float)angle * density + 0.5F);
        }

    }

    public void setBackgroundShapeStroke(int width, int color){
        this.mStrokeWidth = width;
        this.mStrokeColor = color;
        if(mStrokeWidth != 0){
            if (mStrokePaint == null)  {
                mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                mStrokePaint.setStyle(Paint.Style.STROKE);
            }
            mStrokePaint.setStrokeWidth(mStrokeWidth);
            mStrokePaint.setColor(mStrokeColor);
        }else{
            mStrokePaint = null;
        }
    }

    public void setBackgroundColor(int normalColor) {
        this.setBackgroundColor(normalColor, 0);
    }

    public void setBackgroundColor(int normalColor, int pressColor) {
        this.mNormalColor = normalColor;
        this.mPressedColor = pressColor;
        this.setPressEffectEnable(pressColor != 0);
        if(this.mNormalColor != 0) {
            this.mNormalPaint = new Paint();
            this.mNormalPaint.setStyle(Paint.Style.FILL);
            this.mNormalPaint.setAntiAlias(true);
            this.mNormalPaint.setColor(normalColor);
        }

        if(this.mPressedColor != 0) {
            this.mPressedPaint = new Paint();
            this.mPressedPaint.setStyle(Paint.Style.FILL);
            this.mPressedPaint.setAntiAlias(true);
            this.mPressedPaint.setColor(pressColor);
        }

    }

    public void setPressedColor(int pressColor) {
        this.setPressEffectEnable(pressColor != 0);
        this.mIconPressedColor = pressColor;
    }

    public void setPressEffectEnable(boolean isEnable) {
        this.mIsPressEffect = isEnable;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if(this.mIsPressEffect) {
            if(this.isPressed()) {
                if(BG_SHAPE_NONE == this.mBgShape) {
                    if(this.mIconPressedColor != 0) {
                        this.setColorFilter(this.mIconPressedColor, PorterDuff.Mode.SRC_ATOP);
                    }
                } else {
                    this.mIsSelected = true;
                    this.invalidate();
                }
            } else if(BG_SHAPE_NONE == this.mBgShape) {
                this.clearColorFilter();
            } else {
                this.mIsSelected = false;
                this.invalidate();
            }

        }
    }

    protected void onDraw(Canvas canvas) {
        final boolean haveStroke =  mStrokePaint != null &&
                mStrokePaint.getStrokeWidth() > 0 && mStrokePaint.getAlpha() > 0 ;
        if(this.mBgShape == BG_SHAPE_NONE) {
            super.onDraw(canvas);
        } else {
            if(this.mIsSelected) {
                if(this.mIsPressEffect && this.mPressedPaint != null) {
                    if(this.mBgShape == BG_SHAPE_CIRCULAR) {
                        this.drawCircle(canvas, this.mPressedPaint, haveStroke ? mStrokePaint : null);
                    } else if(this.mBgShape == BG_SHAPE_ROUNDED_SQUARE) {
                        this.drawRect(canvas, this.mPressedPaint, haveStroke ? mStrokePaint : null);
                    }
                }
            } else if(this.mBgShape == BG_SHAPE_CIRCULAR) {
                this.drawCircle(canvas, this.mNormalPaint, haveStroke ? mStrokePaint : null);
            } else if(this.mBgShape == BG_SHAPE_ROUNDED_SQUARE) {
                this.drawRect(canvas, this.mNormalPaint, haveStroke ? mStrokePaint : null);
            }

            super.onDraw(canvas);
        }
    }

    private void drawCircle(Canvas canvas, Paint paint, Paint strokePaint) {
        int radius = this.getMeasuredWidth() / 2;
        canvas.drawCircle((float)radius, (float)radius, (float)radius, paint);
        final boolean haveStroke = mStrokePaint.getAlpha() > 0 && mStrokePaint != null &&
                mStrokePaint.getStrokeWidth() > 0;
        if(strokePaint != null){
            canvas.drawCircle((float)radius, (float)radius, (float)radius, strokePaint);
        }

    }

    private void drawRect(Canvas canvas, Paint paint, Paint strokePaint) {
        if(this.mSquareRect == null) {
            this.mSquareRect = new RectF();
            this.mSquareRect.left = 0.0F;
            this.mSquareRect.top = 0.0F;
            this.mSquareRect.right = (float)this.getMeasuredWidth();
            this.mSquareRect.bottom = (float)this.getMeasuredWidth();
        }
        canvas.drawRoundRect(this.mSquareRect, (float)this.mAngle, (float)this.mAngle, paint);
        if(strokePaint != null){
            canvas.drawRoundRect(this.mSquareRect, (float)this.mAngle, (float)this.mAngle, strokePaint);
        }

    }

    protected int dip2px(float dpValue) {
        float scale = this.getContext().getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }
}
