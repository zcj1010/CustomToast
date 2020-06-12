package com.hanjx.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.widget.FrameLayout;

import androidx.annotation.IntDef;

public class NotificationToast extends CustomToast {
    public static final int TOP = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    @IntDef({TOP, BOTTOM, LEFT, RIGHT})
    public @interface SlideOrientation {}

    private int slideOffset;
    private int orientation;

    private String propertyName;
    private int animOffset;

    @Override
    public CustomToast setLayoutParams(FrameLayout.LayoutParams layoutParams) {
        super.setLayoutParams(layoutParams);
        return this;
    }

    @Override
    public NotificationToast setMargin(int left, int top, int right, int bottom) {
        super.setMargin(left, top, right, bottom);
        return this;
    }

    @Override
    public NotificationToast setGravity(int gravity) {
        super.setGravity(gravity);
        return this;
    }

    @Override
    public NotificationToast setShowTime(long showTime) {
        super.setShowTime(showTime);
        return this;
    }

    @Override
    public NotificationToast setShowAnimTime(long showAnimTime) {
        super.setShowAnimTime(showAnimTime);
        return this;
    }

    @Override
    public NotificationToast setDismissAnimTime(long dismissAnimTime) {
        super.setDismissAnimTime(dismissAnimTime);
        return this;
    }

    public NotificationToast setSlideOffset(int slideOffset) {
        this.slideOffset = slideOffset;
        setupAnimOffset();
        return this;
    }

    public NotificationToast setOrientation(@SlideOrientation int orientation) {
        this.orientation = orientation;
        if (orientation == LEFT || orientation == RIGHT) {
            propertyName = "translationX";
        } else {
            propertyName = "translationY";
        }
        setupAnimOffset();
        return this;
    }

    private void setupAnimOffset() {
        animOffset = orientation == BOTTOM || orientation == RIGHT ? slideOffset : -slideOffset;
    }

    @Override
    protected Animator buildShowAnim() {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator transAnim = ObjectAnimator.ofFloat(getCustomView(), propertyName,
                animOffset, 0);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(getCustomView(), "alpha",
                0, 1);
        animatorSet.playTogether(transAnim, alphaAnim);
        return animatorSet;
    }

    @Override
    protected Animator buildDismissAnim() {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator transAnim = ObjectAnimator.ofFloat(getCustomView(), propertyName,
                0, animOffset);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(getCustomView(), "alpha",
                1, 0);
        animatorSet.playTogether(transAnim, alphaAnim);
        return animatorSet;
    }
}
