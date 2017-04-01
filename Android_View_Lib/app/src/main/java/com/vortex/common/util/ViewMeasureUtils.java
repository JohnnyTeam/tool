package com.vortex.common.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.vortex.common.listener.MeasureViewCallback;

/**
 * <p>Title:ViewMeasureUtils.java</p>
 * <p>Description:控件测量工具类</p>
 *
 * @author Johnny.xu
 * @date 2016年12月13日
 */
public class ViewMeasureUtils {

    /**
     * 测量控件
     *
     * @param view
     */
    public static void measureView(final View view, final MeasureViewCallback callback) {

        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                int width = view.getWidth();
                int height = view.getHeight();
                if (callback != null) {
                    callback.callback(view, width, height);
                }
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    /**
     * listview 全部显示
     */
    public static void setListViewMatchHeight(ListView noScrollListView1, int spaceHeight) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = noScrollListView1.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, noScrollListView1);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = noScrollListView1.getLayoutParams();
        params.height = totalHeight + (spaceHeight * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        noScrollListView1.setLayoutParams(params);
    }

    /**
     * gridview 全部显示
     */
    public static void setGridViewMatchHeight(GridView noScrollListView1, int Column, int spaceHeight) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = noScrollListView1.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount() % Column == 0 ? listAdapter.getCount() / Column : listAdapter.getCount() / Column + 1; i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, noScrollListView1);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = noScrollListView1.getLayoutParams();
        params.height = totalHeight + (spaceHeight * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        noScrollListView1.setLayoutParams(params);
    }


}
