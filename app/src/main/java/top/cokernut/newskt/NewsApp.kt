package top.cokernut.newskt

import android.app.Application
import android.content.Context

import top.cokernut.newskt.utils.ActivityManager

class NewsApp : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {

        private lateinit var INSTANCE: NewsApp

        fun getInstance(): Context {
            return INSTANCE
        }

        fun exitAll() {
            ActivityManager.getInstance().popAllActivity()
            System.exit(0)
        }
    }
}
