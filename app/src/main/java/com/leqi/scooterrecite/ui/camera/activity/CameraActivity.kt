package com.leqi.scooterrecite.ui.camera.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.*
import com.bumptech.glide.Glide
import com.huantansheng.easyphotos.EasyPhotos
import com.huantansheng.easyphotos.models.album.entity.Photo
import com.leqi.baselib.base.viewModel.BaseViewModel
import com.leqi.commonlib.util.FileTool
import com.leqi.commonlib.util.MediaUtil
import com.leqi.scooterrecite.R
import com.leqi.scooterrecite.base.BaseActivity
import com.leqi.scooterrecite.ui.camera.CameraManager
import com.leqi.scooterrecite.util.GlideEngine
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.coroutines.launch
import java.io.File


class CameraActivity : BaseActivity<BaseViewModel>(), LifecycleOwner {


    private var mCameraManager: CameraManager? = null

    companion object {

    }

    override fun layoutId(): Int = R.layout.activity_camera

    override fun initParam() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.transparent))
        ScreenUtils.setFullScreen(this)
        //初始化权限
        initPermission()
        //拍照
        camera_take_photo.setOnClickListener {
            takePhoto()
        }
        backImg.setOnClickListener {
          finish()
        }

        //打开相册
        albumImg.setOnClickListener {
            album()
        }
        //闪光灯切换
        cameraFlashModeImg.setOnClickListener {
            switchFlashMode()
        }

    }


    override fun initData() {

    }

    override fun createObserver() {

    }


    /**
     * 初始化权限操作
     */
    fun initPermission() {
        PermissionX.init(this).permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, "需要使用相机权限用来拍摄证件照的照片", "同意", "不同意")
            }.onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList, "需要使用相机权限和文件权限用来制作证件照的照片，请在权限设置勾选页面,勾选相机和存储", "去设置", "取消")
            }.request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    //有权限
                    previewView.post {
                        mCameraManager = CameraManager()
                        mCameraManager!!.init(this@CameraActivity, previewView)
                    }

                } else {
                    // ToastUtils.showShort("您拒绝了如下权限：$deniedList")
                }
            }
    }


    /**
     * 拍照
     */
    private fun takePhoto() {

        if (PermissionUtils.isGranted(Manifest.permission.CAMERA)) {
            launch {
                showLoading("请稍候...")
                mCameraManager?.takePhoto(object : CameraManager.takePictureListener {
                    override fun onSuccess(uri: Uri) {
                        LogUtils.d("拍照成功啦${uri.path}")
                        uri.path?.let {
                            FileTool.galleryAddPic(File(it), this@CameraActivity)


                        }
                        camera_take_photo.isClickable = true
                    }

                    override fun onError() {
                        camera_take_photo.isClickable = true
                        ToastUtils.showShort("拍照出错，请重试！")
                        dismissLoading()
                    }
                })
            }
        } else {
            initPermission()
        }


    }


    /**
     * 打开相册
     */
    private fun album() {

        EasyPhotos.createAlbum(this, false, GlideEngine.getInstance()).setPuzzleMenu(false).setCleanMenu(false).start(10000)
    }

    /**
     * 设置相册的第一张图片的预览
     */
    private fun setAlbumPreview() {
        //预览相册第一张图
        runOnUiThread {
            val uri = MediaUtil.getAlbumFirstPhoto(this.applicationContext)
            Glide.with(this.applicationContext).load(uri).into(albumImg)
        }
    }


    /**
     * 切换闪光灯模式
     */
    private fun switchFlashMode() {
        if (!PermissionUtils.isGranted(Manifest.permission.CAMERA)) {
            return
        }
        mCameraManager?.switchFlashMode()
        if (mCameraManager?.getFlashMode() == ImageCapture.FLASH_MODE_OFF) {
            cameraFlashModeImg.setImageResource(R.mipmap.cmaera_flash_off)
        } else {
            cameraFlashModeImg.setImageResource(R.mipmap.camera_flash_auto)
        }
    }


    /**
     * 切换摄像头
     */
    private fun switchCamera() {
        if (!PermissionUtils.isGranted(Manifest.permission.CAMERA)) {
            return
        }
        mCameraManager?.switchCamera()
    }

    /**
     * 监听音量键进行拍摄
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if (!isLoadingDialogShowing()) {

                LogUtils.d("监听到音量键按下")
                takePhoto()
                return true
            } else {
                LogUtils.d("Dialog 正在显示")
            }
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return true
        }
        return false
    }

    /**
     * 处理相册里选择的图片
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK && data != null && requestCode == 10000) {
            val resultPhotos: ArrayList<Photo> = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS)!!
            if (resultPhotos.size == 0) {
                ToastUtils.showShort("选择的图片为空文件，请重新选择")
                this.finish()
                return
            }
            val imageByteArray = FileTool.file2Byte(resultPhotos[0].path)
            if (imageByteArray == null) {
                ToastUtils.showShort("选择的图片为空文件，请重新选择")
                return
            }
            if (imageByteArray.size > 15 * 1024 * 1024) {
                ToastUtils.showShort("图片文件过大，无法进行正常检测，请换其他相片")
                return
            }

        }
    }


}