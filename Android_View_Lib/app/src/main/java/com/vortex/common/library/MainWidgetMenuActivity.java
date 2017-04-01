package com.vortex.common.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vortex.common.base.CnBaseActivity;

/**
 * 控件主菜单页面
 *
 * @author Johnny.xu
 *         date 2017/3/15
 */
public class MainWidgetMenuActivity extends CnBaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        findViewById(R.id.btn_test_progress).setOnClickListener(this);
    }

    @Override
    public int getContentViewById() {
        return R.layout.cn_main_widge_menu;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
//            case R.id.btn_test_progress:
//                startActivity(ProgressBarTestActivity.class);
//                break;
            default:
                break;
        }
    }

    public void startActivity(Class cla) {
        startActivity(new Intent(mContext, cla));
    }
}
