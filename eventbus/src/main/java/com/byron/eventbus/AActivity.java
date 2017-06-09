package com.byron.eventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AActivity extends AppCompatActivity {

    private ImageView image_view_a;
    private TextView text_view_a;
    private Button button_a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        EventBus.getDefault().register(this);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        image_view_a = (ImageView) findViewById(R.id.image_view_a);
        text_view_a = (TextView) findViewById(R.id.text_view_a);
        button_a = (Button) findViewById(R.id.button_a);
    }

    private void initData() {

    }

    private void initListener() {
        button_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpBActivity();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateTextView(MessageEvent event){
        text_view_a.setText(event.message);
    }

    private void jumpBActivity(){
        startActivity(new Intent(this, BActivity.class));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(this, "onNewIntent", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
