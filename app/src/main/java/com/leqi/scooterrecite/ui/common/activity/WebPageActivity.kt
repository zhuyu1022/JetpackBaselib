package com.leqi.scooterrecite.ui.common.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.blankj.utilcode.util.LogUtils
import com.leqi.baselib.base.viewModel.BaseViewModel
import com.leqi.scooterrecite.R
import com.leqi.scooterrecite.base.BaseActivity
import kotlinx.android.synthetic.main.common_activity_webpage.*

/**
 * 描述：通用的webview
 * 作者：ZHUYU
 * 日期：2019/9/27 8:59
 */
class WebPageActivity : BaseActivity<BaseViewModel>() {

    companion object {
        fun actionStart(context: Context, url: String) {
            val intent = Intent(context, WebPageActivity::class.java)
            intent.putExtra("url", url)
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        }
    }


    override fun layoutId(): Int = R.layout.common_activity_webpage

    override fun initView(savedInstanceState: Bundle?) {
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        webView.webViewClient = webViewClient
        webView.webChromeClient = webChromeClient
        var url = intent.getStringExtra("url")
        if (!TextUtils.isEmpty(url)) {
            if (url != null) {
                webView.loadUrl(url)
            }
        }

    }

    override fun createObserver() {

    }


    override fun initData() {
    }

    private val webChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            LogUtils.d("progress: $newProgress")
            if (newProgress == 100) {
                webView_progress.visibility = View.GONE
            } else {
                webView_progress.progress = newProgress
            }
        }
    }

    /**
     * 在加载完成的时候将标题设置为网页标题
     */
    private val webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (URLUtil.isNetworkUrl(url)) {
                return false
            }
            if (url.startsWith("tel:")) {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            } else {
                view.loadUrl(url)
            }
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            if (view.title != url) {

                titleView.setTitleText(view.title)
            } else {
                titleView.setTitleText(resources.getString(R.string.app_name))
            }
            LogUtils.d("view.title:${view.title}")
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
            return
        }
        super.onBackPressed()
    }


}