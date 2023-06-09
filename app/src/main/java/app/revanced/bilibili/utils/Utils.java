package app.revanced.bilibili.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;


public class Utils {
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    private static String mobiApp = "";

    public static Context getContext() {
        if (context == null) {
            LogHelper.error(() -> "Context is null");
        }
        return context;
    }

    @SuppressLint("DiscouragedApi")
    public static int getResId(String name, String type) {
        return context.getResources().getIdentifier(name, type, context.getPackageName());
    }

    public static String getString(String idName) {
        return context.getString(getResId(idName, "string"));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public static Drawable getDrawable(String idName) {
        return context.getDrawable(getResId(idName, "drawable"));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public static Drawable getDrawable(Context context, String idName) {
        return context.getDrawable(getResId(idName, "drawable"));
    }

    public static int getColor(String idName) {
        return context.getColor(getResId(idName, "color"));
    }

    public static int getColor(Context context, String idName) {
        return context.getColor(getResId(idName, "color"));
    }

    public static ColorStateList getColorStateList(String idName) {
        return context.getColorStateList(getResId(idName, "drawable"));
    }

    public static ColorStateList getColorStateList(Context context, String idName) {
        return context.getColorStateList(getResId(idName, "drawable"));
    }

    public static void reboot() {
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(context.getPackageName());
        if (intent != null)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityManager am = context.getSystemService(ActivityManager.class);
        if (am != null)
            for (ActivityManager.AppTask task : am.getAppTasks())
                task.finishAndRemoveTask();
        context.startActivity(intent);
        System.exit(0);
    }

    public static String getMobiApp() {
        if (TextUtils.isEmpty(mobiApp)) {
            try {
                mobiApp = context.getPackageManager()
                        .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
                        .metaData.getString("MOBI_APP");
                if (TextUtils.isEmpty(mobiApp)) {
                    switch (context.getPackageName()) {
                        case Constants.BLUE_PACKAGE_NAME:
                            mobiApp = "android_b";
                            break;
                        case Constants.PLAY_PACKAGE_NAME:
                            mobiApp = "android_i";
                            break;
                        case Constants.HD_PACKAGE_NAME:
                            mobiApp = "android_hd";
                            break;
                        default:
                            mobiApp = "android";
                            break;
                    }
                }
            } catch (PackageManager.NameNotFoundException ignored) {
            }
        }
        return mobiApp;
    }

    public static boolean isPink() {
        return "android".equals(getMobiApp());
    }

    public static boolean isBlue() {
        return "android_b".equals(getMobiApp());
    }

    public static boolean isPlay() {
        return "android_i".equals(getMobiApp());
    }

    public static boolean isHd() {
        return "android_hd".equals(getMobiApp());
    }
}