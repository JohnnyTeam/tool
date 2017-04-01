package com.vortex.common.demo;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.vortex.common.base.CnBaseActivity;
import com.vortex.common.library.R;
import com.vortex.common.view.CnCloudLayout;

/**
 * <p>Title:CloudLayoutTestActivity.java</p>
 * <p>Description:云布局测试页面</p>
 * @author Johnny.xu
 * @date 2017年1月6日
 */
public class CloudLayoutTestActivity extends CnBaseActivity {

	private CnCloudLayout cloud_tag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cn_base_cloud_test);

		cloud_tag = (CnCloudLayout)findViewById(R.id.cloud_tag);
		initValues();
		cloud_tag.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				initValues();
			}
		});
	}
	int index = 20;
	private void initValues() {
		cloud_tag.removeAllViews();
		for (int i = 0; i < index; i++) {
			View view = View.inflate(mContext, R.layout.cn_simple_text_view, null);
			TextView tv = (TextView) view.findViewById(R.id.cn_tv_simple_text);

			LayoutParams vp = tv.getLayoutParams();
			vp.width = 120;
			vp.height = 60;
			tv.setText("index " +  i);
			tv.setBackgroundColor(getResources().getColor(R.color.theme_70_color));
			tv.setLayoutParams(vp);
			cloud_tag.addView(view);
		}
		index --;
	}

}
