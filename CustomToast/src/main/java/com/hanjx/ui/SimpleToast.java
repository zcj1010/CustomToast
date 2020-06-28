package com.hanjx.ui;

import android.view.Gravity;
import android.view.View;

public class SimpleToast extends CustomToast {
    public static void toastShort(View view) {
        new SimpleToast()
                .setupDefault()
                .toastView(view);
    }

    public static void toastLong(View view) {
        new SimpleToast()
                .setupLong()
                .toastView(view);
    }

    public static void toastTime(View view, long showTime) {
        new SimpleToast()
                .setupDefault()
                .setShowTime(showTime)
                .toastView(view);
    }

    public SimpleToast setupDefault() {
        gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        bottomMargin = (int) (getScreenHeight() * 0.1f);
        showAnimTime = 300;
        dismissAnimTime = 250;
        showTime = 1500;
        return this;
    }

    public SimpleToast setupLong() {
        setupDefault();
        showTime = 3000;
        return this;
    }

    public int getScreenHeight() {
        return getActivity().getResources().getDisplayMetrics().heightPixels;
    }
}
