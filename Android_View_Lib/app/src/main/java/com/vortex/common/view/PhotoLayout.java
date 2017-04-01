package com.vortex.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vortex.common.library.R;
import com.vortex.common.entity.PhotoModel;
import com.vortex.common.factory.CnDialogFactory;
import com.vortex.common.util.DensityUtils;
import com.vortex.common.view.dialog.OnDialogClickListener;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * <p>Title:PredicateLayout.java</p>
 * <p>Description:照片布局文件</p>
 *
 * @author Johnny.xu
 *         date 2016年9月30日
 */
public class PhotoLayout extends LinearLayout {

    int mLeft, mRight, mTop, mBottom;

    Hashtable<View, Position> map = new Hashtable<View, Position>();

    private Padding mPadding;
    private Context mContext;

    private float mWidgetPadding; // 子控件间距
    private int showRowNum; // 每行显示数目

    public List<PhotoModel> mPhotoModels;

    private PhotoClickListener mClickListener;
    private PhotoNumberChangedListener mNumberChangedListener;

    // 展示模式，true 代表选择添加模式，false代表展示模式
    private boolean mShowModel;

    /**
     * 最多选择图片数量
     */
    private int mMaxShowNum = 6;

    // 标记，表示现在是否可以添加图片
    private boolean isAddMode;

    private void initShowModel() {
        if (mShowModel) {
            initAddView();
        }
    }

    public PhotoLayout(Context context) {
        this(context, null);
    }

    public PhotoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        TypedArray attrArray = getResources().obtainAttributes(attrs, R.styleable.PhotoLayout);
        mShowModel = attrArray.getBoolean(R.styleable.PhotoLayout_showModel, false);
        mMaxShowNum = attrArray.getInteger(R.styleable.PhotoLayout_maxImgNum, 3);
        mWidgetPadding = attrArray.getDimension(R.styleable.PhotoLayout_plPadding, 16);
        initShowModel();
        attrArray.recycle();
    }

    public PhotoLayout(Context context, AttributeSet attrs, int a) {
        super(context, attrs, a);
        this.mContext = context;
        mPhotoModels = new ArrayList<PhotoModel>();
    }

    public void setListener(PhotoClickListener listener) {
        mClickListener = listener;
    }

    public void setNumberChangedListener(PhotoNumberChangedListener listener) {
        this.mNumberChangedListener = listener;
    }

    public synchronized void addView(PhotoModel photoModel) {
        if (photoModel != null) {
            mPhotoModels.add(photoModel);
            addPhotoView(photoModel);

            if (mNumberChangedListener != null) {
                mNumberChangedListener.isChanged(true, mPhotoModels.size());
            }
        }
    }

    public synchronized void addAllView(List<PhotoModel> data) {
        this.addAllView(data, false);
    }

    private synchronized void addAllView(List<PhotoModel> data, boolean isAdd) {
        if (!isAdd) {
            mPhotoModels.clear();
            removeAllViews();
        }

        if (data != null && data.size() > 0) {
            mPhotoModels.addAll(data);
            for (PhotoModel model : data) {
                addPhotoView(model);
            }
            if (mNumberChangedListener != null) {
                mNumberChangedListener.isChanged(true, mPhotoModels.size());
            }
        }
    }

    private void addPhotoView(final PhotoModel photoModel) {
        if (mPhotoModels.size() >= mMaxShowNum && isAddMode && mShowModel) {
            removeAddView();
        }
        View imgView = getChildView(photoModel);
        addView(imgView, isAddMode ? getChildCount() - 1 : getChildCount());
        requestLayout();
    }

    private View getChildView(final PhotoModel photoModel) {
        View view = View.inflate(mContext, R.layout.cn_base_photo_view, null);
        ImageView iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
        ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
        iv_photo.setTag(photoModel);
        view.setTag(photoModel);
        if (mShowModel) {
            int padding = DensityUtils.dp2px(mContext, 8);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params.setMargins(0, padding, padding, 0);
            iv_photo.setLayoutParams(params);
            iv_delete.setVisibility(View.VISIBLE);
            iv_delete.setTag(photoModel);
            iv_delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteDialog((PhotoModel) v.getTag());
                }
            });
        } else {
            iv_delete.setVisibility(View.GONE);
        }
        iv_photo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onClickImage((PhotoModel) v.getTag());
                }
            }
        });
        if (mClickListener != null) {
            mClickListener.loadImage(photoModel, iv_photo);
        }
        return view;
    }

    private void deleteDialog(final PhotoModel photoModel) {

        CnDialogFactory.createSimpleDialog(mContext, "确定删除此照片吗？", new OnDialogClickListener() {
            @Override
            public void onConfirmClick(String edit) {
                removeImageView(photoModel);
            }
        });
    }

//    /**
//     * 浏览照片大图
//     */
//    protected void showFullPicPopMenu(final PhotoModel photoModel) {
//        View view = View.inflate(mContext, R.layout.common_pic_imageview, null);
//        ImageView pic = (ImageView) view.findViewById(R.id.ivphoto);
//        ImageButton back = (ImageButton) view.findViewById(R.id.back);
//        ImageButton delete = (ImageButton) view.findViewById(R.id.delete);
//        final PopupWindow mPicPopWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        ColorDrawable dw = new ColorDrawable(R.color.main_bg);
//        delete.setVisibility(model ? View.VISIBLE : View.GONE);
//        mPicPopWindow.setFocusable(true);
//        mPicPopWindow.setOutsideTouchable(true);
//        mPicPopWindow.setBackgroundDrawable(dw);
//        mPicPopWindow.setAnimationStyle(android.R.style.Animation_Toast);
//        mPicPopWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//        //相对父控件的位置
//        if (photoModel.isWebImg) {
//            bitmapUtils.display(pic, Constants.IMAGE_URL + photoModel.url, bitmapDisplayConfig);
//            MyLog.i("ImageUrl", Constants.IMAGE_URL + photoModel.url);
//        } else {
//            bitmapUtils.display(pic, Uri.fromFile(new File(photoModel.url)).toString(), bitmapDisplayConfig);
//        }
//        delete.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                deleteDialog(photoModel);
//                mPicPopWindow.dismiss();
//            }
//        });
//        back.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPicPopWindow.dismiss();
//            }
//        });
//        view.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPicPopWindow.dismiss();
//            }
//        });
//    }

    /**
     * 控件点击事件点击回调
     */
    public interface PhotoClickListener {

        void onClickAdd();

        void onClickImage(PhotoModel model);

        void loadImage(PhotoModel model, ImageView view);
    }

    /**
     * 图片数量变化监听
     */
    public interface PhotoNumberChangedListener {
        void isChanged(boolean isAdd, int num);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int mWidth = MeasureSpec.getSize(widthMeasureSpec);

        mPadding = new Padding(getPaddingLeft(), getPaddingRight(), getPaddingTop(), getPaddingBottom());

        int mCount = getChildCount();

        mLeft = 0;
        mRight = 0;
        mTop = 0;
        mBottom = 0;

        int innerWidth = 0; // 内宽度
        int d_value = 0; // 差值 为排满后还剩几位

        for (int i = 0; i < mCount; i++) {
            final View child = getChildAt(i);

            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            // 此处增加onLayout中的换行判断，用于计算所需的高度
            int childw = child.getMeasuredWidth();
            int childH = child.getMeasuredHeight();
            if (mPadding.padding == 0) {
                innerWidth = mWidth - mPadding.paddingLeft - mPadding.paddingRight;
                mPadding.height = childH;
                // 优先测量每行放多少列 间距多少
                showRowNum = (int) ((innerWidth + mWidgetPadding) / (childw + mWidgetPadding));
                showRowNum = showRowNum == 0 ? 1 : showRowNum;

                mPadding.padding = (innerWidth - showRowNum * childw) / ( showRowNum - 1);
                mPadding.width = childw;
            }

            Position position = new Position();
            mLeft = getPosition(i);

            mRight = mLeft + mPadding.width;
            if (i % showRowNum == 0) {
                if (i == 0) {
                    mTop = mPadding.paddingTop;
                } else {
                    mTop += mWidgetPadding + mPadding.height;
                }
            }
            mBottom = mTop + mPadding.height;
            position.left = mLeft;
            position.top = mTop;
            position.right = mRight;
            position.bottom = mBottom;
            map.put(child, position);
        }
        setMeasuredDimension(mWidth, mBottom + mPadding.paddingBottom);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(1, 1);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            Position pos = map.get(child);
            if (pos != null) {
                child.layout(pos.left, pos.top, pos.right, pos.bottom);
            } else {
                Log.i("MyLayout", "error");
            }
        }

    }


    private class Position {
        int left, top, right, bottom;
    }

    private class Padding {
        public Padding(int paddingLeft, int paddingRight,
                       int paddingTop, int paddingBottom){
            this.paddingLeft = paddingLeft;
            this.paddingRight = paddingRight;
            this.paddingTop = paddingTop;
            this.paddingBottom = paddingBottom;
        }

        public int paddingLeft, paddingRight,
                paddingTop, paddingBottom, padding/** 子控件间距 */, width, height;
    }

    public int getPosition(int IndexInRow) {
        int index = IndexInRow % showRowNum;
        return mPadding.paddingLeft + (mPadding.width + mPadding.padding) * index;
    }

    /**
     * 添加初始按钮(添加照片)
     * 必须是添加模式
     */
    public void initAddView() {
        if (mShowModel && isAddMode) {
            return;
        }
        //初始化照片界面
        View view = View.inflate(mContext, R.layout.cn_base_photo_view, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_photo);
        int padding = DensityUtils.dp2px(mContext, 8);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.setMargins(0, padding, padding, 0);
        iv.setLayoutParams(params);
        iv.setImageResource(R.drawable.common_add_img);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null)
                    mClickListener.onClickAdd();
            }
        });
        addView(view, getChildCount());
        isAddMode = true;
    }

    public void removeImageView(PhotoModel photoModel) {
        mPhotoModels.remove(photoModel);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            Object obj = view.getTag();
            if (obj != null && photoModel.equals(obj)) {
                removeView(view);
                break;
            }
        }

        if (mPhotoModels.size() < mMaxShowNum && !isAddMode && mShowModel) {
            initAddView();
        }

        if (mNumberChangedListener != null) {
            mNumberChangedListener.isChanged(false, mPhotoModels.size());
        }
    }

    public void removeAddView() {
        if (mShowModel && isAddMode) {
            isAddMode = false;
            removeViewAt(getChildCount() - 1);
        }
    }

}