package com.smartrefreshlistapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.smartrefreshlistapplication.apdaters.MyListAdapter;
import com.smartrefreshlistapplication.base_adapter.BaseRecyclerAdapter;
import com.smartrefreshlistapplication.base_adapter.SmartViewHolder;

import java.util.Arrays;

import ezy.ui.layout.LoadingLayout;
import photoview.com.smartrefreshlistapplication.R;

/**
 * SmartRefreshLayout---“智能”下拉刷新布局
 * 参看：https://blog.csdn.net/lknlll/article/details/77988978
 */
public class MainActivity extends AppCompatActivity  {

    private static String[] provinces = new String[]{
            "北京","天津","上海","重庆",
            "黑龙江","吉林","辽宁","河北","河南","山东","江苏","山西","陕西","甘肃","四川","青海","湖南","湖北","江西","安徽","浙江","福建","广东","广西","贵州","云南","海南",
            "内蒙古","新疆维吾尔族自治区","宁夏回族自治区","西藏","宁夏回族自治区",
            "香港","澳门"
    };

    private BaseRecyclerAdapter<String> mAdapter;
    private LoadingLayout mLoadingLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        //
        mLoadingLayout = (LoadingLayout)findViewById(R.id.loading);
        String[] empty = new String[0];
        if (empty.length == 0){
            mLoadingLayout.setEmptyImage(R.mipmap.ic_launcher);
            mLoadingLayout.showEmpty();
        }

        mAdapter = new MyListAdapter(android.R.layout.simple_list_item_1);
        recyclerView.setAdapter(mAdapter);

        final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(SpinnerStyle.FixedBehind).setPrimaryColorId(R.color.colorPrimary).setAccentColorId(android.R.color.white));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setEnableAutoLoadMore(false);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //刷新的数据
                        mAdapter.refresh(Arrays.asList(provinces));
                        //停止刷新
                        refreshLayout.finishRefresh();
                        //隐藏空布局，显示实际数据内容
                        mLoadingLayout.showContent();
                        //
                        refreshLayout.setNoMoreData(false);
                    }
                }, 2000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mAdapter.getItemCount() > 80) {
                            Toast.makeText(getApplication(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                            refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                        } else {
                            mAdapter.loadMore(Arrays.asList(provinces));
                            refreshLayout.finishLoadMore();
                        }
                    }
                }, 2000);
            }
        });

        //触发自动刷新
        refreshLayout.autoRefresh();
    }
}
