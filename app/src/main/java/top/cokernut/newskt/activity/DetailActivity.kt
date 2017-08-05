package top.cokernut.newskt.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import top.cokernut.newskt.R
import top.cokernut.newskt.base.BaseActivity


class DetailActivity(private var mUrl: String? = null, private var mImgUrl: String? = null) : BaseActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mUrl = intent.getStringExtra(URL_STR)
        mImgUrl = intent.getStringExtra(URL_IMG)
        val options = RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
        Glide.with(this)
                .load(mImgUrl)
                .apply(options)
                .into(img)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webSettings.setAppCacheEnabled(true)
        webSettings.databaseEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.defaultTextEncodingName = "utf-8"
        webView.webChromeClient = ChromeClient()
        webView.webViewClient = ViewClient()
        webView.loadUrl(mUrl)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "提示信息", Snackbar.LENGTH_LONG)
                    .setAction("动作", {Toast.makeText(this@DetailActivity, "使用了动作", Toast.LENGTH_SHORT).show()}).show()
        }
    }

    private inner class ChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
           /* if (newProgress == 100) {

            } else if (newProgress != 100) {

            }*/
        }

        override fun onShowCustomView(view: View, callback: WebChromeClient.CustomViewCallback) {
            super.onShowCustomView(view, callback)
        }

        override fun onReceivedTitle(view: WebView, title: String?) {
            super.onReceivedTitle(view, title)
            title?.let {
                toolbar_layout.title = title
            }
        }
    }

    private inner class ViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            url?.let {
                view.loadUrl(url)
            }
            return true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val URL_STR = "url"
        val URL_IMG = "img"
    }
}
