package com.hhb.myplugindemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hhb.annotation.DotAnnotation;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShow();
            }
        });
    }

    @DotAnnotation(value = 1)
    private void onShow() {
        button.setText("已经执行");

    }

    private void initView() {
        button = (Button) findViewById(R.id.button);
    }
}