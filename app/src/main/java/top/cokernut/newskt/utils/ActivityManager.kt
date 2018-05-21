package top.cokernut.newskt.utils

import android.support.v7.app.AppCompatActivity
import java.util.*

/**
 * Created by Administrator on 2017/7/20.
 */
class ActivityManager private constructor() {

    fun popActivity() {
        val activity: AppCompatActivity? = activityStack?.lastElement()
        activity?.finish()
    }

    fun popActivity(activity: AppCompatActivity?) {
        activity?.let {
            activity.finish()
            activityStack?.remove(activity)
        }
    }

    fun currentActivity(): AppCompatActivity? {
        val activity = activityStack?.lastElement()
        return activity
    }

    fun pushActivity(activity: AppCompatActivity) {
        activityStack?.add(activity)
    }

    fun popAllActivityExceptOne(cls: Class<*>) {
        while (true) {
            val activity = currentActivity() ?: break
            if (activity.javaClass == cls) {
                break
            }
            popActivity(activity)
        }
    }

    fun popAllActivity() {
        while (true) {
            val activity = currentActivity() ?: break
            popActivity(activity)
        }
    }

    companion object {
        private var activityStack: Stack<AppCompatActivity>? = Stack<AppCompatActivity>()

        fun getInstance(): ActivityManager {
            return ActivityManager()
        }
    }
}