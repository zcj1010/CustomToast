package com.hanjx.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ActivityUtils;

import java.lang.ref.WeakReference;

public class CustomToast {
    protected WeakReference<View> customViewRef;

    protected int leftMargin;
    protected int topMargin;
    protected int rightMargin;
    protected int bottomMargin;
    protected int gravity;
    protected FrameLayout.LayoutParams layoutParams;
    
    protected long showTime;
    protected long showAnimTime;
    protected long dismissAnimTime;

    protected Animator animator;

    protected AnimatorListenerAdapter defaultShowAnimListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            getCustomView().setAlpha(1);
        }
    };
    protected AnimatorListenerAdapter defaultDismissAnimListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            removeCustomView();
        }
    };

    public CustomToast setLayoutParams(FrameLayout.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
        return this;
    }

    public CustomToast setMargin(int left, int top, int right, int bottom) {
        this.leftMargin = left;
        this.topMargin = top;
        this.rightMargin = right;
        this.bottomMargin = bottom;
        return this;
    }

    public CustomToast setGravity(int gravity) {
        this.gravity = gravity;
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

    public void toastView(View customView) {
        if (customView.getParent() != null) {
            ((ViewGroup) customView.getParent()).removeView(customView);
        }
        removeCustomView();
        this.customViewRef = new WeakReference<>(customView);
        Activity activity = getActivity();
        FrameLayout decorView = (FrameLayout) activity.getWindow().getDecorView();
        customView.setAlpha(0);
        decorView.addView(customView, layoutParams != null ? layoutParams : buildLayoutParams(activity.getResources()));
        performShowAnim();
        customView.postDelayed(this::performDismissAnim, showTime);
    }

    private void performShowAnim() {
        if (getCustomView() == null) {
            return;
        }
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        animator = buildShowAnim();
        if (animator.getDuration() <= 0) {
            animator.setDuration(showAnimTime);
        }
        animator.addListener(getShowListener());
        animator.start();
    }

    private void performDismissAnim() {
        if (getCustomView() == null) {
            return;
        }
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        animator = buildDismissAnim();
        if (animator.getDuration() <= 0) {
            animator.setDuration(dismissAnimTime);
        }
        animator.addListener(getDismissListener());
        animator.start();
    }

    protected Animator buildShowAnim() {
        Animator animator = ObjectAnimator.ofFloat(getCustomView(), "alpha", 0, 1);
        animator.setDuration(showAnimTime);
        return animator;
    }

    protected Animator buildDismissAnim() {
        Animator animator = ObjectAnimator.ofFloat(getCustomView(), "alpha", 1, 0);
        animator.setDuration(showAnimTime);
        return animator;
    }

    protected AnimatorListenerAdapter getShowListener() {
        return defaultShowAnimListener;
    }

    protected AnimatorListenerAdapter getDismissListener() {
        return defaultDismissAnimListener;
    }

    private FrameLayout.LayoutParams buildLayoutParams(Resources resources) {
        int statusBarHeight = 0;
        int statusBarId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (statusBarId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(statusBarId);
        }

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = gravity;
        lp.leftMargin = leftMargin;
        lp.topMargin = statusBarHeight + topMargin;
        lp.rightMargin = rightMargin;
        lp.bottomMargin = bottomMargin;
        return lp;
    }

    public View getCustomView() {
        return customViewRef == null ? null : customViewRef.get();
    }

    public void removeCustomView() {
        if (getCustomView() != null) {
            View customView = customViewRef.get();
            if (customView.getParent() != null) {
                ((ViewGroup) customView.getParent()).removeView(customView);
            }
            customViewRef.clear();
        }
    }

    public Activity getActivity() {
        if (customViewRef == null || customViewRef.get() == null) {
            return ActivityUtils.getTopActivity();
        }
        return ActivityUtils.getActivityByContext(customViewRef.get().getContext());
    }
}
