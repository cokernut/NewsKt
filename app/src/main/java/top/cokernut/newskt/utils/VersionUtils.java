package top.cokernut.newskt.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import top.cokernut.newskt.NewsApp;

public class VersionUtils {

    public static String setUpVersionName(Context context) {
        String versionName = null;
        try {
            versionName = context.getApplicationContext().getPackageManager()
                    .getPackageInfo(context.getApplicationContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static int getVerisonCode() {
        int versionCode = 0;
        try {
            versionCode = NewsApp.Companion.getInstance().getPackageManager().getPackageInfo(NewsApp.Companion.getInstance().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}
