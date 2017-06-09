package com.byron.butterknife;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by company-ios on 2017/6/8.
 */

public class MyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<User> datas;

    public MyAdapter(Context context, ArrayList<User> datas) {
        this.context = context;
        this.datas = datas;
    }

    public ArrayList<User> getDatas() {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        return datas;
    }

    public void setDatas(ArrayList<User> datas) {
        if (datas == null) {
            datas = new ArrayList<>();
        } else {
            this.datas = datas;
        }
    }

    @Override
    public int getCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_user, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvUsername.setText(datas.get(position).getUsername());
        holder.tvPassword.setText(datas.get(position).getPassword());
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_password)
        TextView tvPassword;
        @BindView(R.id.btn_delete)
        Button btnDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
