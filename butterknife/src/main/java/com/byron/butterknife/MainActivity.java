package com.byron.butterknife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.list_view)
    ListView listView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        adapter = new MyAdapter(this, null);
        listView.setAdapter(adapter);
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        Log.e(TAG, "username = " + username + " ,password = " + password);
        Toast.makeText(this, "Hello ButterKnife", Toast.LENGTH_SHORT).show();

        ArrayList<User> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User("username"+i, "password"+i);
            datas.add(user);
        }
        adapter.setDatas(datas);
        adapter.notifyDataSetChanged();
    }
}
