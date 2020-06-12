package com.hanjx.ui;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;

public class SimpleToast extends CustomToast {
    public static void toastShort(Activity activity, View view) {
        new SimpleToast()
                .setupDefault(activity)
                .toastView(activity, view);
    }

    public static void toastLong(Activity activity, View view) {
        new SimpleToast()
                .setupLong(activity)
                .toastView(activity, view);
    }

    public static void toastTime(Activity activity, View view, long showTime) {
        new SimpleToast()
                .setupDefault(activity)
                .setShowTime(showTime)
                .toastView(activity, view);
    }

    public SimpleToast setupDefault(Activity activity) {
        gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        bottomMargin = (int) (getScreenHeight(activity) * 0.1f);
        showAnimTime = 200;
        dismissAnimTime = 200;
        showTime = 1500;
        return this;
    }

    public SimpleToast setupLong(Activity activity) {
        setupDefault(activity);
        showTime = 3000;
        return this;
    }

    public int getScreenHeight(Activity activity) {
        return activity.getResources().getDisplayMetrics().heightPixels;
    }
}
