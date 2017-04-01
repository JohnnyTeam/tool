package com.vortex.common.demo;

import android.os.Bundle;

import com.vortex.common.base.CnBaseActivity;
import com.vortex.common.library.R;
import com.vortex.common.view.ProgressBarView;

/**
 * 进度条测试页面
 *
 * @author Johnny.xu
 *         date 2017/3/15
 */
public class ProgressBarTestActivity extends CnBaseActivity {

    private ProgressBarView pbv_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pbv_view = (ProgressBarView)findViewById(R.id.pbv_view);
        pbv_view.setProgress(50);
    }

    @Override
    public int getContentViewById() {
        return R.layout.activity_progress_bar;
    }
}
