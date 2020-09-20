package com.leqi.commonlib.util

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore

/**
 * @Description:    媒体库相关工具类
 * @Author:         ZHUYU
 * @CreateDate:     2020/6/4 10:12
 * @UpdateRemark:   更新说明：
 * @UpdateDate:     2020/6/4 10:12
 * @Version:        1.0
 */
object MediaUtil {
    /**
     * 获取相册第一张图片
     * */
    fun getAlbumFirstPhoto(context: Context): Uri? {
        val cursor = context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
            "${MediaStore.MediaColumns.DATE_ADDED} desc")
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                return uri
            }
            cursor.close()
        }
        return null
    }

}