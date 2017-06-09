package com.byron.eventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CActivity extends AppCompatActivity {

    private ImageView image_view_c;
    private TextView text_view_c;
    private Button button_c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
        EventBus.getDefault().register(this);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        image_view_c = (ImageView) findViewById(R.id.image_view_c);
        text_view_c = (TextView) findViewById(R.id.text_view_c);
        button_c = (Button) findViewById(R.id.button_c);
    }

    private void initData() {

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void updateTextView(MessageEvent event){
        text_view_c.setText(event.message);
    }

    private void initListener() {
        button_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("Hello EventBus"));
                jumpAActivity();
            }
        });
    }

    private void jumpAActivity() {
        startActivity(new Intent(this, AActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
