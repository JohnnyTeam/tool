package com.vortex.common.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.vortex.common.library.R;
import com.vortex.common.listener.CnActionBarListener;
import com.vortex.common.listener.CnSimulateRequestListener;
import com.vortex.common.listener.IActivityOperationCallback;
import com.vortex.common.util.VorLog;
import com.vortex.common.util.VorToast;
import com.vortex.common.view.CnActionBar;
import com.vortex.common.view.RefreshDialogView;

import org.xutils.common.Callback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.vortex.common.manager.UtilManager.filtrationClass;

/**
 * 基础页面
 *
 * @author Johnny.xu
 *         date 2017/2/11
 */
public abstract class CnBaseActivity extends FragmentActivity implements IActivityOperationCallback {

	protected AlertDialog mAlertDialog;

	protected Context mContext;

	protected CnActionBar mActionBar;

	private RefreshDialogView mRefreshView;

	private LinearLayout mContentView;

	private LinearLayout mCoverContentView;

	private List<Callback.Cancelable> mReqList;

	protected InputMethodManager imm;

	private View cover_view; // 遮盖层

	private View tv_net_work_error; // 网络错误层

	public static Set<Activity> activities = new HashSet<Activity>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		VorLog.i("appDebug launchName", this.getClass().getSimpleName());

		mReqList = new ArrayList<Callback.Cancelable>();

		mContext = this;
		setContentView(R.layout.cn_test_page);

		_initView();
		initActionBar(mActionBar);
		_initContentView();
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mRefreshView.setVisibility(false);

		getWindow().setBackgroundDrawable(null);
		if (!filtrationClass.contains(this.getClass())) {
			activities.add(this);
		}
	}

	@Override
	public void hideKeyboard(View view) {
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	@Override
	public void showKeyboard(View view) {
		imm.showSoftInputFromInputMethod(view.getWindowToken(),
				InputMethodManager.SHOW_FORCED);
	}

	@Override
	public boolean hideCoverView() {
		if (cover_view != null && cover_view.getVisibility() == View.VISIBLE) {
			cover_view.setVisibility(View.GONE);
			return true;
		}
		return false;
	}

	@Override
	public void showCoverView() {
		cover_view.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean hideRequestView() {
		return mRefreshView.hideDialog();
	}

	@Override
	public void showRequestView() {
		mRefreshView.showDialog();
	}

	@Override
	public void showRequestView(String prompt) {
		mRefreshView.showDialog(prompt);
	}

	@Override
	public void hideNetWorkErrorView() {
		tv_net_work_error.setVisibility(View.GONE);
	}

	@Override
	public void showNetWorkErrorView() {
		tv_net_work_error.setVisibility(View.VISIBLE);
	}

	@Override
	public void showCoverContentView() {
		mCoverContentView.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean hideCoverContentView() {
		if (mCoverContentView != null && mCoverContentView.getVisibility() == View.VISIBLE) {
			mCoverContentView.setVisibility(View.GONE);
			return true;
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		if (onExitAction()) {

			super.onBackPressed();
		}
	}

	/**
	 * 退出操作
	 * @return true为没拦截错误，可退出
	 */
	public boolean onExitAction() {
		return !hideRequestView() && !hideCoverView();
	}

	private PopupWindow.OnDismissListener mOnDismissListener = new PopupWindow.OnDismissListener() {
		@Override
		public void onDismiss() {
			hideCoverView();
		}
	};

	/**
	 * 初始化内容布局
	 */
	private void _initContentView() {

		if (getContentViewById() != 0) {
			getLayoutInflater().inflate(getContentViewById(), mContentView);
		} else if (getContentViewByView() != null) {
			setContentView(getContentViewByView());
		}

		if (getCoverContentViewById() != 0) {
			getLayoutInflater().inflate(getContentViewById(), mCoverContentView);
		} else if (getCoverContentViewByView() != null) {
			mCoverContentView.removeAllViews();
			mCoverContentView.addView(getContentViewByView());
		}
	}

	@Override
	public void initCoverContentView(View view) {
		mCoverContentView.removeAllViews();
		mCoverContentView.addView(view);
	}

	public int getContentViewById() {
		return  0;
	}

	public View getContentViewByView() {
		return null;
	}

	public int getCoverContentViewById() {
		return  0;
	}

	public View getCoverContentViewByView() {
		return null;
	}

	private void _initView() {
		mActionBar = (CnActionBar) findViewById(R.id.base_view_cab);
		mRefreshView = (RefreshDialogView) findViewById(R.id.base_view_rdv);
		mContentView = (LinearLayout) findViewById(R.id.base_ll_content);
		mCoverContentView = (LinearLayout) findViewById(R.id.base_cover_content);
		cover_view = findViewById(R.id.cover_view);
		tv_net_work_error = findViewById(R.id.tv_net_work_error);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		activities.remove(this);
		cancelRequestHttp();
		if (mAlertDialog != null && mAlertDialog.isShowing()) {
			mAlertDialog.dismiss();
			mAlertDialog = null;
		}
	}

	protected void exitApp() {
		for (Activity activity : activities) {
			activity.finish();
		}
		System.exit(0);
	}

	/**
	 * 初始化标题栏
	 */
	protected void initActionBar(CnActionBar actionBar) {
		actionBar.setListener(new DefaultActionBarListener());
	}

	/**
	 * 弱提示
	 */
	protected void showToast(final String prompt) {

		runOnUiThread(new Runnable() {
			public void run() {

				VorToast.showShort(mContext, prompt);
			}
		});
	}

	/**
	 * 跳转页面
	 */
	public void startActivity(Class cla) {
		Intent intent = new Intent(this, cla);
		super.startActivity(intent);
	}

	/**
	 * 跳转页面
	 */
	public void startActivity(Class cla, Bundle data) {

		Intent intent = new Intent(this, cla);
		if (data != null) {
			intent.putExtras(data);
		}
		super.startActivity(intent);
	}

	/**
	 * 跳转页面
	 */
	public void startActivity(Class cla, String key, Object obj) {

		Intent intent = new Intent(this, cla);
		if (obj instanceof Serializable) {
			Bundle bundle = new Bundle();
			bundle.putSerializable(key, (Serializable) obj);
			intent.putExtras(bundle);
		}
		super.startActivity(intent);
	}

	/**
	 * 关闭标题栏
	 */
	public void closeTitleBar() {
		mActionBar.setVisibility(View.GONE);
	}

	public boolean checkAlertDialog() {
		return mAlertDialog != null && mAlertDialog.isShowing();
	}

	/**
	 * 模拟请求方式
	 */
	protected void simulateRequest(long time, final CnSimulateRequestListener listener) {
		showRequestView();
		mContentView.postDelayed(new Runnable() {
			@Override
			public void run() {
				hideRequestView();
				if (listener != null) {
					listener.onSuccess();
				}
			}
		}, time);
	}

	protected Intent getIntent(Class cla) {
		return new Intent(mContext, cla);
	}

	protected class  DefaultActionBarListener extends CnActionBarListener {
		@Override
		public void clickLeft(View v) {
			hideKeyboard(v);
			onBackPressed();
		}
	};

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
	}

}
