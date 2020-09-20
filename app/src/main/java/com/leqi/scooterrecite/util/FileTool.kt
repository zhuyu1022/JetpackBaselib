package com.leqi.commonlib.util

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Base64
import androidx.exifinterface.media.ExifInterface
import com.blankj.utilcode.util.LogUtils
import com.leqi.scooterrecite.config.Config
import okhttp3.ResponseBody
import java.io.*
import java.text.DecimalFormat

/**
 * 文件工具
 * @Author: zhuxiaoyao
 * @Date: 2018-12-5 10:39:21
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2018-12-5 10:39:26
 * @Description: institute
 * @Email: zhuxs@venpoo.com
 */
object FileTool {

    /**
     *@param filePath
     *@return Base64
     * 文件转Base64
     */
    fun encodeBase64File(filePath: String): String? {
        if (!TextUtils.isEmpty(filePath)) {
            val file = File(filePath)
            val fis: FileInputStream
            var buffer: ByteArray? = null
            try {
                fis = FileInputStream(file)
                buffer = ByteArray(file.length().toInt())
                fis.read(buffer)
                fis.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return if (buffer != null) {
                Base64.encodeToString(buffer, Base64.DEFAULT)
            } else {
                null
            }
        }
        return null
    }

    /**
     *@param path
     *@return Boolean
     * 删除文件
     */
    fun deleteFile(context: Context, path: String): Boolean {
        fileDirectoryExists()
        val file = File(Config.BASE_FOLDER_NAME + File.separator + path)
        if (file.exists() && file.isFile) {
            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.path)))
            return file.delete()
        }
        return false
    }

    /**
     *@param file
     *@return Boolean
     * 删除文件
     */
    fun deleteFile(context: Context, file: File): Boolean {
        fileDirectoryExists()
        if (file.exists() && file.isFile) {
            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.path)))
            LogUtils.d("文件删除成功")
            return file.delete()
        }
        return false
    }


    /**
     *@param imageByte
     *@return file.name
     * 保存文件
     */
    fun tempFile(context: Context, imageByte: ByteArray): String {
        val fileBitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
        return saveFileWithSuffix(context, fileBitmap)
    }

    /**
     *@param bitmap
     *@return file.name
     * 保存文件 非图片文件
     */
    fun saveFile(bitmap: Bitmap): String {
        fileDirectoryExists()
        val file = File(Config.BASE_FOLDER_NAME + "/" + "123.jpg")
        val fileOutputStream: FileOutputStream?
        try {
            fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file.name
    }

    fun saveFile(byteArray: ByteArray, name: String): String {
        fileDirectoryExists()
        val file = File(Config.BASE_FOLDER_NAME + "/" + "name$name")
        val fileOutputStream: FileOutputStream?
        try {
            fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(byteArray)
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file.name
    }

    private fun fileDirectoryExists() {
        // 判断文件目录是否存在
        //检查文件夹权限
        val fileDirectory = File(Config.BASE_FOLDER_NAME)
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs()
        }
    }

    /**
     *@param bitmap
     *@return file.name
     * 保存图片文件
     */
    fun saveFileWithSuffix(context: Context, bitmap: Bitmap): String {
        fileDirectoryExists()
                    val file = File(Config.BASE_FOLDER_NAME + File.separator + System.currentTimeMillis() + "zjz.jpg")
                    val fos: FileOutputStream
                    try {
                        fos = FileOutputStream(file)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                        fos.flush()
                        fos.close()
                        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.path)))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file.name
    }

    fun getReadableFileSize(size: Long): String {
        if (size <= 0) {
            return "0"
        }
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(size / Math.pow(1024.0, digitGroups.toDouble())) + " " + units[digitGroups]
    }

    /**
     * @param body
     * @param fileName
     * @return  extractIdPhoto.absolutePath
     * 提供文件名 保存文件
     * 文件内容是 ResponseBody来的输入流
     */
    fun writeResponseBody2Disk(body: ResponseBody?, fileName: String, context: Context): Boolean {
        if (body == null) {
            LogUtils.d("ResponseBody来的输入流为空~ ")
            return false
        }
        try {
            fileDirectoryExists()
            val extractIdPhoto = File(Config.BASE_FOLDER_NAME + File.separator + fileName)
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val fileReader = ByteArray(1024)
                inputStream = body.byteStream()
                if (inputStream == null) {
                    return false
                }
                outputStream = FileOutputStream(extractIdPhoto)
                while (true) {
                    val read = inputStream.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                }
                outputStream.flush()
                //保存到系统相册，通知相册更新 有时候名字存储不完整会导致FileNotFoundException
                galleryAddPic(extractIdPhoto, context)
                LogUtils.d("path::${extractIdPhoto.absolutePath}")
                return true
            } catch (e: IOException) {
                return false
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            return false
        }
    }

    /**
     *@param file
     * 保存到系统相册，通知相册更新
     */
      fun galleryAddPic(file: File, context: Context) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val contentUri = Uri.fromFile(file)
        mediaScanIntent.data = contentUri
        context.sendBroadcast(mediaScanIntent)
    }

    /**
     *@param uri
     *@return data
     * 获取文件 路径path
     */
    fun uri2StringPath(uri: Uri?, context: Context): String? {
        if (null == uri) return null
        val scheme = uri.scheme
        LogUtils.d("scheme :: $scheme")
        var path: String? = null
        when (scheme) {
            null -> path = uri.path
            ContentResolver.SCHEME_FILE -> path = uri.path
            ContentResolver.SCHEME_CONTENT -> {
                val cursor = context.contentResolver.query(uri, arrayOf(MediaStore.Images.Media.DATA), null, null, null)
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        val index = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                        if (index > -1) {
                            path = cursor.getString(index)
                        }
                    }
                    cursor.close()
                }
            }
        }
        LogUtils.d("文件路径 path: " + path!!)
        return path
    }

    /**
     *@param filePath
     *@return byteArray
     * 文件路径获取文件   文件转byteArray
     */
    fun file2Byte(filePath: String): ByteArray? {
        var byteArray: ByteArray? = null
        try {
            val file = File(filePath)
            val fis = FileInputStream(file)
            val bos = ByteArrayOutputStream(1000)
            val b = ByteArray(1000)
            var n: Int
            do {
                n = fis.read(b)
                if (n == -1) break
                bos.write(b, 0, n)
            } while (true)
            byteArray = bos.toByteArray()
            fis.close()
            bos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return byteArray
    }

    /**
     * @param inputStream
     * @return result.toString()
     * InputStream 转 String
     */
    fun inputStream2String(inputStream: InputStream): String {
        val result = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var mLength: Int
        do {
            mLength = inputStream.read(buffer)
            if (mLength == -1) break
            result.write(buffer, 0, mLength)
        } while (true)
        return result.toString()
    }

    /**
     * @param inputStream
     * @return buffer
     * InputStream 转 byte
     */
    fun inputStream2Byte(inputStream: InputStream): ByteArray? {
        val buffer = inputStream.readBytes()
        inputStream.close()
        return buffer
    }

    /**
     * @param bitmap
     * @return byteArrayOutputStream.toByteArray()
     */
    fun bitmap2ByteArray(bitmap: Bitmap, status: Int, quality: Int): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        when (status) {
            0 -> bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
            1 -> bitmap.compress(Bitmap.CompressFormat.PNG, quality, byteArrayOutputStream)
        }
        return byteArrayOutputStream.toByteArray()
    }

    /**
     * @param byteArray
     * @return byteArray.size / 1024
     * 获取图片 大小  (默认大小单位MB)
     */
    fun imageFileSize(byteArray: ByteArray?): Int {
        return if (byteArray != null) {
            byteArray.size / 1024
        } else 0
    }

    /**
     * @param path
     * @return size
     */
    fun path2fileSize(path: String): Int {
        val filepath = Config.BASE_FOLDER_NAME + File.separator + path
        return (File(filepath).length() / 1024).toInt()
    }

    /**
     * 图片质量压缩，根据指定大小压缩图片,不大于指定大小
     * @param image
     * @param maxSize
     */
    fun compressImage(image: Bitmap, maxSize: Int): Bitmap? {
        val outputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream) //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到outputStream中
        var options = 100
        while (outputStream.toByteArray().size / 1024 > maxSize) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            outputStream.reset() //重置outputStream即清空outputStream
            image.compress(Bitmap.CompressFormat.JPEG, options, outputStream) //这里压缩options%，把压缩后的数据存放到outputStream中
            options -= 1 //每次都减少10
        }

        LogUtils.d("compressImage---options:$options")
        LogUtils.d("调整文件大小后 ：： ${outputStream.toByteArray().size / 1024}")
        val byteArray = ByteArrayInputStream(outputStream.toByteArray()) //把压缩后的数据outputStream存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(byteArray, null, null)
    }

    /**
     *  @return 确定图片旋转方向
     *
     */
    fun readExif(path: String): Int {
        var exif: ExifInterface? = null
        val orientation: String
        try {
            exif = ExifInterface(path)
        } catch (ex: IOException) {
            LogUtils.e("cannot read exif")
        }
        orientation = if (exif != null) {
            exif.getAttribute(ExifInterface.TAG_ORIENTATION)!!
        } else {
            "0"
        }
        return when (orientation.toInt()) {
            0 -> {
                360
            }
            1 -> {
                360
            }
            3 -> {
                180
            }
            6 -> {
                90
            }
            8 -> {
                270
            }
            else -> {
                360
            }
        }
    }

    /**
     * 处理旋转照片
     * @param bitmap 原位图
     * @param turn   旋转的角度
     * @return 处理过后的图片
     */
    fun dealBitmap(bitmap: Bitmap, turn: Float): Bitmap {
        val matrix = Matrix()
        matrix.setRotate(turn)
        LogUtils.d("dealBitmap")
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    fun bitmapToBase64(bitmap: Bitmap?): String {
        var result: String = ""
        var baos: ByteArrayOutputStream? = null
        try {
            if (bitmap != null) {
                baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                baos.flush()
                baos.close()
                val bitmapBytes = baos.toByteArray()
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                if (baos != null) {
                    baos.flush()
                    baos.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return result
    }

    /**
     * base64转为bitmap
     *
     * @return
     */
    fun base64ToBitmap(base64Data: String): Bitmap {
        val bytes = Base64.decode(base64Data, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    fun go2System(context: Context) {
        try {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val fromParts = Uri.fromParts("package", context.packageName, null)
            intent.data = fromParts
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } catch (e: Exception) {
            LogUtils.e("go2System::$e")
        }
    }

}
