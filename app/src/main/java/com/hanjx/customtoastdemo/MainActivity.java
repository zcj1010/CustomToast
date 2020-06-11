package com.hanjx.customtoastdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hanjx.ui.CustomToast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = new TextView(this);
        textView.setPadding(100, 0, 100, 0);
        textView.setGravity(Gravity.CENTER);
        textView.setText("测试");
        textView.setTextSize(30);
        textView.setTextColor(0xFFFFFFFF);
        textView.setBackgroundColor(0xFF3C7CFC);

        View view = findViewById(R.id.text);
        view.setOnClickListener(v -> {
            new CustomToast()
                    .setSlideOffset(100)
                    .setMargin(200)
                    .setShowAnimTime(200)
                    .setDismissAnimTime(200)
                    .setShowTime(2500)
                    .toastView(this, textView);
        });
    }
}