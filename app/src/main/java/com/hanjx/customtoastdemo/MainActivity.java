package com.hanjx.customtoastdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hanjx.ui.CustomToast;
import com.hanjx.ui.NotificationToast;
import com.hanjx.ui.SimpleToast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = new TextView(this);
        textView.setPadding(100, 0, 100, 0);
        textView.setGravity(Gravity.CENTER);
        textView.setText("测试1");
        textView.setTextSize(30);
        textView.setTextColor(0xFFFFFFFF);
        textView.setBackgroundColor(0xFF3C7CFC);

        TextView textView2 = new TextView(this);
        textView2.setPadding(100, 0, 100, 0);
        textView2.setGravity(Gravity.CENTER);
        textView2.setText("测试2");
        textView2.setTextSize(30);
        textView2.setTextColor(0xFFFFFFFF);
        textView2.setBackgroundColor(0xFF3C7CFC);

        TextView textView3 = new TextView(this);
        textView3.setPadding(100, 0, 100, 0);
        textView3.setGravity(Gravity.CENTER);
        textView3.setText("测试3");
        textView3.setTextSize(30);
        textView3.setTextColor(0xFFFFFFFF);
        textView3.setBackgroundColor(0xFF3C7CFC);

        View view = findViewById(R.id.text);
        view.setOnClickListener(v -> {
            new CustomToast()
                    .setMargin(0, 400, 0, 0)
                    .setShowTime(2000)
                    .setShowAnimTime(300)
                    .setDismissAnimTime(300)
                    .setGravity(Gravity.CENTER_HORIZONTAL)
                    .toastView(this, textView);

            new NotificationToast()
                    .setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM)
                    .setMargin(0, 0, 0, 600)
                    .setSlideOffset(100)
                    .setOrientation(NotificationToast.BOTTOM)
                    .setShowTime(2000)
                    .setShowAnimTime(400)
                    .setDismissAnimTime(400)
                    .toastView(this, textView2);

            SimpleToast.toastLong(this, textView3);
        });
    }
}
