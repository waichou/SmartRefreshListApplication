package com.smartrefreshlistapplication.apdaters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;

import com.smartrefreshlistapplication.base_adapter.BaseRecyclerAdapter;
import com.smartrefreshlistapplication.base_adapter.SmartViewHolder;

import java.util.Collection;

/**
 * @author zhouwei
 * @date 2018/8/31
 * @time 14:08
 * @desc
 */
public class MyListAdapter extends BaseRecyclerAdapter<String> {

    public MyListAdapter(int layoutId) {
        super(layoutId);
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, String model, int position) {
        holder.text(android.R.id.text1, model);
    }
}
