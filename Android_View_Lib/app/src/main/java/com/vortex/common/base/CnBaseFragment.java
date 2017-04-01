package com.vortex.common.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment基础类
 *
 * @author Johnny.xu
 *         date 2017/2/16
 */
public class CnBaseFragment extends BaseFragment {

    private List<Callback.Cancelable> mReqList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mReqList = new ArrayList<Callback.Cancelable>();
    }

    /**
     * 基础跳转
     */
    public void startActivity(Class cla) {
        Intent intent = new Intent(getActivity(), cla);
        mContext.startActivity(intent);
    }

    /**
     * 基础跳转-带条件
     */
    public void startActivity(Class cla, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cla);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    /**
     * 调用此方法前提条件为继承CnBaseActivity
     */
    protected void showToast(String prompt) {
        if (mCnBaseActivity != null) {
            mCnBaseActivity.showToast(prompt);
        }
    }

    /**
     * 添加请求到请求列表中
     */
    public void addCancelList(Callback.Cancelable request) {
        mReqList.add(request);
    }

    /**
     * 移出所有请求
     */
    public void cancelRequestHttp() {
        for (Callback.Cancelable req : mReqList) {
            if (req != null) {
                req.cancel();
            }
        }
        mReqList.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelRequestHttp();
    }
}
