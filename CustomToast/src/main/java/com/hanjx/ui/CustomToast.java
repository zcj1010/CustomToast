package com.hanjx.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;

public class CustomToast {
    private WeakReference<View> customViewRef;
    private int margin;
    private int slideOffset;
    private long showTime;
    private long showAnimTime;
    private long dismissAnimTime;

    private AnimatorSet animatorSet;

    public CustomToast setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    public CustomToast setSlideOffset(int slideOffset) {
        this.slideOffset = slideOffset;
        return this;
    }

    public CustomToast setShowTime(long showTime) {
        this.showTime = showTime;
        return this;
    }

    public CustomToast setShowAnimTime(long showAnimTime) {
        this.showAnimTime = showAnimTime;
        return this;
    }

    public CustomToast setDismissAnimTime(long dismissAnimTime) {
        this.dismissAnimTime = dismissAnimTime;
        return this;
    }

    public void toastView(Activity activity, View view) {
        removeCustomView();
        this.customViewRef = new WeakReference<>(view);
        view.setTranslationY(-slideOffset);
        FrameLayout decorView = (FrameLayout) activity.getWindow().getDecorView();
        decorView.addView(view, buildLayoutParams(activity.getResources()));
        performAnim(true);
        view.postDelayed(() -> performAnim(false), showTime);
    }

    public FrameLayout.LayoutParams buildLayoutParams(Resources resources) {
        int statusBarHeight = 0;
        int statusBarId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (statusBarId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(statusBarId);
        }

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        lp.topMargin = statusBarHeight + margin;
        return lp;
    }

    public void performAnim(boolean show) {
        View customView = customViewRef.get();
        if (customView == null) {
            return;
        }

        if (animatorSet != null && animatorSet.isRunning()) {
            animatorSet.cancel();
        }

        ObjectAnimator transAnim = ObjectAnimator.ofFloat(customView, "translationY",
                show ? -slideOffset : 0, show ? 0 : -slideOffset);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(customView, "alpha",
                show ? 0 : 1, show ? 1 : 0);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(transAnim, alphaAnim);
        animatorSet.setDuration(show ? showAnimTime : dismissAnimTime);

        if (!show) {
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    removeCustomView();
                }
            });
        }

        animatorSet.start();
    }

    public void removeCustomView() {
        if (customViewRef != null && customViewRef.get() != null) {
            View customView = customViewRef.get();
            if (customView.getParent() != null) {
                ((ViewGroup) customView.getParent()).removeView(customView);
            }
            customViewRef.clear();
        }
    }
}
