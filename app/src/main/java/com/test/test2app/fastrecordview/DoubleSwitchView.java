package com.test.test2app.fastrecordview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.test.test2app.S;


/**
 * created by zhaoyuntao
 * on 2019-10-23
 * description:
 * this button can share its long click event with AudioRecordView
 */
public class DoubleSwitchView extends FrameLayout {

    private View defaultView;
    private View secondView;

    public static final int INDEX_DEFAULT = 0;
    public static final int INDEX_SECOND = 1;
    private ValueAnimator animator;
    private int index = INDEX_DEFAULT;

    public DoubleSwitchView(Context context) {
        super(context);
        init(null);
    }

    public DoubleSwitchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DoubleSwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        setPaddingRelative(0, 0, 0, 0);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        S.s("init:"+count);
        if (count >= 2) {
            secondView = getChildAt(0);
            defaultView = getChildAt(1);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        S.s("onMeasure");
        int w_max = MeasureSpec.getSize(widthMeasureSpec);
        int h_max = MeasureSpec.getSize(heightMeasureSpec);
        if (defaultView != null) {
            measureChild(defaultView, widthMeasureSpec, heightMeasureSpec);
        }
        if (secondView != null) {
            measureChild(secondView, widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(w_max, h_max);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        S.s("onLayout");
        super.onLayout(changed, left, top, right, bottom);
        if (defaultView != null) {
            int width = defaultView.getWidth();
            int height = defaultView.getHeight();
            int leftInParent = ((right - left) - width) / 2;
            int rightInParent = leftInParent + width;
            int topInParent = ((bottom - top) - height) / 2;
            int bottomInParent = topInParent + height;
            defaultView.layout(leftInParent, topInParent, rightInParent, bottomInParent);
        }
        if (secondView != null) {
            int width = secondView.getWidth();
            int height = secondView.getHeight();
            int leftInParent = ((right - left) - width) / 2;
            int rightInParent = leftInParent + width;
            int topInParent = ((bottom - top) - height) / 2;
            int bottomInParent = topInParent + height;
            secondView.layout(leftInParent, topInParent, rightInParent, bottomInParent);
        }
    }

    public void setDefaultView(View view) {
        if (view != null) {
            this.defaultView = view;
            this.defaultView.setVisibility(VISIBLE);
            addView(defaultView);
        }
    }

    public void setSecondView(View view) {
        if (view != null) {
            this.secondView = view;
            this.secondView.setVisibility(GONE);
            addView(secondView);
        }
    }

    public void nextIndex() {
        if (index == INDEX_DEFAULT) {
            index = INDEX_SECOND;
        } else {
            index = INDEX_DEFAULT;
        }
        startAnim();
    }

    public int getIndex() {
        return index;
    }

    public void switchIndex(int index) {
        if (this.index == index) {
            return;
        }
        if (index == INDEX_DEFAULT) {
            this.index = index;
        } else if (index == INDEX_SECOND) {
            this.index = index;
        }
        startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
    }

    private void startAnim() {
        if (defaultView == null || secondView == null) {
            return;
        }
        stopAnim();
        if (animator == null) {
            animator = ValueAnimator.ofFloat(1000, 0);
            animator.setDuration(300);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                private LayoutParams layoutParamsDefault;
                private LayoutParams layoutParamsSecond;
                private float percentDisappear = 1;
                private float percentAppear = 0;
                private int widthOfChild;
                private int heightOfChild;

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    if (layoutParamsDefault == null || layoutParamsSecond == null) {
                        layoutParamsDefault = (LayoutParams) defaultView.getLayoutParams();
                        layoutParamsSecond = (LayoutParams) secondView.getLayoutParams();
                        widthOfChild = defaultView.getWidth();
                        heightOfChild = defaultView.getHeight();
                    }
                    float value = (float) animation.getAnimatedValue();
                    percentDisappear = value / 1000;
                    if (percentDisappear < 0.5) {
                        percentDisappear = 0;
                    }
                    percentAppear = 1 - value / 1000;
                    if (percentAppear < 0.5) {
                        percentAppear = 0;
                    }

                    if (getIndex() == INDEX_SECOND) {
                        defaultView.setClickable(false);
                        secondView.setClickable(true);
                        layoutParamsDefault.width = (int) (widthOfChild * percentDisappear);
                        layoutParamsDefault.height = (int) (heightOfChild * percentDisappear);
                        defaultView.setLayoutParams(layoutParamsDefault);
                        defaultView.setAlpha(percentDisappear);
                        if (percentDisappear == 0) {
                            defaultView.setVisibility(GONE);
                        } else {
                            defaultView.setVisibility(VISIBLE);
                        }

                        layoutParamsSecond.width = (int) (widthOfChild * percentAppear);
                        layoutParamsSecond.height = (int) (heightOfChild * percentAppear);
                        secondView.setLayoutParams(layoutParamsSecond);
                        secondView.setAlpha(percentAppear);
                        if (percentAppear == 0) {
                            secondView.setVisibility(GONE);
                        } else {
                            secondView.setVisibility(VISIBLE);
                        }
                    } else {
                        defaultView.setClickable(true);
                        secondView.setClickable(false);
                        layoutParamsDefault.width = (int) (widthOfChild * percentAppear);
                        layoutParamsDefault.height = (int) (heightOfChild * percentAppear);
                        defaultView.setLayoutParams(layoutParamsDefault);
                        defaultView.setAlpha(percentAppear);
                        if (percentAppear == 0) {
                            defaultView.setVisibility(GONE);
                        } else {
                            defaultView.setVisibility(VISIBLE);
                        }

                        layoutParamsSecond.width = (int) (widthOfChild * percentDisappear);
                        layoutParamsSecond.height = (int) (heightOfChild * percentDisappear);
                        secondView.setLayoutParams(layoutParamsSecond);
                        secondView.setAlpha(percentDisappear);
                        if (percentDisappear == 0) {
                            secondView.setVisibility(GONE);
                        } else {
                            secondView.setVisibility(VISIBLE);
                        }
                    }
                }
            });
        }
        if (!animator.isRunning()) {
            animator.start();
        }
    }

    private void stopAnim() {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            animator.end();
        }
    }

    @Override
    public void destroyDrawingCache() {
        stopAnim();
        super.destroyDrawingCache();
    }

}
