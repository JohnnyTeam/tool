package com.vortex.common.entity;

import com.vortex.common.util.FileUtils;
import com.vortex.common.util.UUIDGenerator;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 照片对象模型
 *
 * @author Johnny.xu
 *         date 2017/2/23
 */
@Table(name = "PhotoModel")
public class PhotoModel {
    @Column(name = "id", isId = true, autoGen = false)
    public String id;
    //资源对象Id
    @Column(name = "resourceId")
    public String resourceId;
    //是否是本地图片
    @Column(name = "isLocalImage")
    public boolean isLocalImage = false;
    //本地图片类型 1.餐企2.稽查3.事件
    @Column(name = "type")
    public int type;

    @Column(name = "imageBase64")
    public String imageBase64;
    //是否上传
    @Column(name = "upload")
    public boolean upload;

    public String imagePath;
    public PhotoModel(){}
    public PhotoModel(boolean isLocalImage, String imagePath) {
        id = UUIDGenerator.getUUID();
        this.isLocalImage = isLocalImage;
        this.imagePath = imagePath;
        if (isLocalImage) {
            this.imageBase64 = FileUtils.getImageBase64Str(imagePath);
        }
    }

    public PhotoModel(String id, String imagePath) {
        this.id = id;
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PhotoModel && this.id.equals(((PhotoModel) o).id);
    }
}
