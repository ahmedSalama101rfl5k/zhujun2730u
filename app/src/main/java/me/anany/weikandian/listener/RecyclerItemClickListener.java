package me.anany.weikandian.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import java.util.List;

import me.anany.weikandian.adapter.HomeRecyclerViewAdapter;
import me.anany.weikandian.model.HomeNewsDataItem;
import me.anany.weikandian.ui.activity.HomeNewsDetailActivity;
import me.anany.weikandian.utils.LogUtil;

/**
 * Created by anany on 16/1/21.
 * <p>
 * Email:zhujun2730@gmail.com
 */
public class RecyclerItemClickListener implements HomeRecyclerViewAdapter.ClickListener {

    List<HomeNewsDataItem> homeNewsDataItems;
    static int position;
    Context mContext;

    public RecyclerItemClickListener(Context context, int position,
                                     List<HomeNewsDataItem> homeNewsDataItems) {
        this.homeNewsDataItems = homeNewsDataItems;
        this.mContext = context;
        this.position = position;
    }

    @Override
    public void onItemClick(int itemListposition, View v, List<HomeNewsDataItem> items) {


        LogUtil.e("itemListposition:" + itemListposition + "，pagerPosition" + position);


        Intent intent = new Intent(mContext, HomeNewsDetailActivity.class);

        if (position == 0 && itemListposition > 1) {

            // 推荐页的列表，因为有头部header，所以数据position会有变化
            intent.putExtra("news_data", items.get(itemListposition - 2));

        } else {
            intent.putExtra("news_data", items.get(itemListposition - 1));

        }
        mContext.startActivity(intent);
    }

    public void setPagerPosition(int position) {
        LogUtil.e("赋值了：" + position);
        this.position = position;
    }
}
