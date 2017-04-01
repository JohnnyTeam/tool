package com.vortex.common.view.pop;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.vortex.common.base.CnBaseAdapter;
import com.vortex.common.library.R;

/**
 * <p>Title:PopupDefaultAdapter.java</p>
 * <p>Description:弹出框默认适配器</p>
 * @author Johnny.xu
 * @date 2016年12月14日
 */
public class PopupDefaultAdapter extends CnBaseAdapter<String, ViewHolderString>{



	public PopupDefaultAdapter(Context context) {
		super(context);
	}

	public PopupDefaultAdapter(Context context, boolean isSlect) {
		super(context);
	}

	@Override
	public int getLayout() {
		return R.layout.cn_pop_item_default_view;
	}

	@Override
	public ViewHolderString getViewHolder(View convertView) {
		ViewHolderString vh = new ViewHolderString();
		vh.tv_prompt = (TextView) convertView.findViewById(R.id.cn_tv_pop_array);
		return vh;
	}

	@Override
	public void initData(int position, ViewHolderString vh) {
		vh.tv_prompt.setText(getItem(position));
	}

}

class ViewHolderString {
	TextView tv_prompt;
}
