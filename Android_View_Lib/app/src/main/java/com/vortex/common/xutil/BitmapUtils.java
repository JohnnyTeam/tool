package com.vortex.common.xutil;

import android.widget.ImageView;

import com.vortex.common.library.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * @anthor wg
 * @time 2017/2/24 15:22
 * @describe xutil工具类 加载网络图片和本地图片
 */

public class BitmapUtils {

    public static void display(ImageView imageView, String url, ImageOptions imageOptions) {
        x.image().bind(imageView, url, imageOptions);
    }

    public static void display(ImageView imageView, String url) {
        x.image().bind(imageView, url, options);
    }

    //    public static void getDefaultImageOptions( ){
//
//    }
    static ImageOptions options = new ImageOptions.Builder()
            //.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
//设置加载过程中的图片
            .setLoadingDrawableId(R.drawable.common_app_stub_image)
//设置加载失败后的图片
            .setFailureDrawableId(R.drawable.ic_load_faild)
//设置使用缓存
            .setUseMemCache(true)
//设置显示圆形，图片控件必须是正方形才能有效
            .setRadius(DensityUtil.dip2px(4))
            .setImageScaleType(ImageView.ScaleType.FIT_XY)
            //  .setCircular(true)
            .setCrop(true)//是否对图片进行裁剪
//设置支持gif
            .setIgnoreGif(false)
            .build();


}
