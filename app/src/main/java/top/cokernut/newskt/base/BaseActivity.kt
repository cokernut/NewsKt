package top.cokernut.newskt.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import top.cokernut.newskt.utils.ActivityManager

/**
 * Created by Administrator on 2017/7/24.
 */
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityManager.getInstance().pushActivity(this)
    }

    override fun onDestroy() {
        ActivityManager.getInstance().popActivity()
        super.onDestroy()
    }

    /**
     * 统一解决返回按钮无用的问题
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}