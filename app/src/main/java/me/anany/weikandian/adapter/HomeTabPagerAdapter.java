package me.anany.weikandian.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedHashMap;
import java.util.List;

import me.anany.weikandian.ui.pager.HomePager;

/**
 * Created by anany on 16/1/8.
 * <p>
 * <p>
 * Tablayout 与 ViewPager 的 Pager数据源
 * <p>
 * Email:zhujun2730@gmail.com
 */
public class HomeTabPagerAdapter extends PagerAdapter {

    private List<HomePager> pagerList;
    private List<String> titleTextList;

    private LinkedHashMap<Integer, View> views = new LinkedHashMap<>();

    public HomeTabPagerAdapter(List<HomePager> pagerList, List<String> titleTextList) {
        this.pagerList = pagerList;
        this.titleTextList = titleTextList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleTextList.get(position);
    }

    @Override
    public int getCount() {
        return pagerList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        HomePager homePager = pagerList.get(position);
        View view = views.get(position);
        if (view == null) {
            view = homePager.inflateView();
            views.put(position, view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
