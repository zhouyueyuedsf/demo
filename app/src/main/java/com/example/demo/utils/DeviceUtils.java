package com.example.demo.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;


import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by yaodh on 2017/3/22.
 */

public class DeviceUtils {
    private static DisplayMetrics deviceDM = null;

    public static int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    public static String getDevicesId(Context context) {
        String id = null;
        if (Build.VERSION.SDK_INT < 23) {
            if (!isEmulator()) {
                id = getAndroidId(context);
            }

            if (TextUtils.isEmpty(id)) {
                id = UUID.randomUUID().toString();
            }
        } else {
            id = getDeviceIdUpM(context);
        }
        return id;
    }

    private static String getDeviceIdUpM(Context context) {
        String sep = "\t";
        String serial = Build.SERIAL;

        if (serial == null) {
            serial = "";
        }

        serial = serial.trim();
        if (serial.length() > 20) {
            serial = serial.substring(0, 20);
        }

        String deviceIdPrefix = sep + sep;
        String deviceId = Base64.encodeToString((deviceIdPrefix +
                Settings.Secure.getString(context.getContentResolver(),
                        "android_id") + sep + serial).getBytes(), 2);
        deviceId = URLEncoder.encode(deviceId);
        return deviceId;
    }

    private static String getAndroidId(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception var2) {
            return "";
        }
    }

    private static boolean isEmulator() {
        return Build.MODEL.equalsIgnoreCase("sdk")
                || Build.MODEL.equalsIgnoreCase("google_sdk")
                || Build.BRAND.equalsIgnoreCase("generic");
    }

    public static boolean isRTL(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= 17 && configuration.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            return true;
        }
        return false;
    }




    /**
     * 考虑到屏幕旋转因素，每次应重新获取一次保证最新
     *
     * @return
     */
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static void hideSystemUI(Window window) {
        int newUiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            newUiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;
            newUiOptions |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            newUiOptions |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            newUiOptions |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            newUiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE;
        }
        window.getDecorView().setSystemUiVisibility(newUiOptions);
    }

    /**
     * 导航栏，状态栏透明
     *
     * @param activity
     */
    public static void setNavigationBarTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
//        ActionBar actionBar = activity.getActionBar();
//        actionBar.hide();
    }


    // This snippet shows the system bars. It does this by removing all the flags
    // except for the ones that make the content appear under the system bars.
    public static void showSystemUI(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getSoftButtonsBarHeight(@NonNull Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    /**
     * 得到除去状态栏的屏幕剩余高度
     *
     * @param window
     * @return
     */
    public static int getScreenAvailableHeight(Window window) {
        Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.height();
    }

    // 获取栈顶 activity 类名
    public static String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        if (manager == null) {
            return null;
        }
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null) {
            return String.valueOf(runningTaskInfos.get(0).topActivity);
        } else {
            return null;
        }
    }

    public static boolean isHorizon(Context context){
        Configuration mConfiguration = context.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        return ori == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
