package com.vortex.common.listener;

import android.view.View;

/**
 * 主页面事件回调监听
 *
 * @author Johnny.xu
 * date 2017/2/13
 */
public interface MainPageEventListener {

    void initMenuView(boolean boo);

    void showRefreshView();

    void showRefreshView(String prompt);

    void hideRefreshView();

    void showCoverView(boolean isShow);

    void hideParentKeyboard(View view);

    void initMenuBar(int num);

}
