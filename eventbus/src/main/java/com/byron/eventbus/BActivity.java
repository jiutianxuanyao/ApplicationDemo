package com.byron.eventbus;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BActivity extends AppCompatActivity {

    private ImageView image_view_b;
    private TextView text_view_b;
    private Button button_b;
    private Button btn_change_name;
    private ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        image_view_b = (ImageView) findViewById(R.id.image_view_b);
        text_view_b = (TextView) findViewById(R.id.text_view_b);
        button_b = (Button) findViewById(R.id.button_b);
        btn_change_name = (Button) findViewById(R.id.btn_change_name);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    private void initData() {

    }

    private void initListener() {
        button_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpCActivity();
            }
        });

        btn_change_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeName();
            }
        });
    }

    private void changeName() {
        progress_bar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progress_bar.setVisibility(View.GONE);
                EventBus.getDefault().postSticky(new MessageEvent("Change Name Finish"));
//                finish();
            }
        }, 2000);
    }

    private void jumpCActivity(){
        startActivity(new Intent(this, CActivity.class));
    }

}
