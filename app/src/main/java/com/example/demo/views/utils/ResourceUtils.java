package com.example.demo.views.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.StringRes;
import androidx.core.content.res.ResourcesCompat;


import com.example.demo.BuildConfig;
import com.example.demo.MyApplication;

import java.util.Locale;

public class ResourceUtils {

    public static final String TAG = "LanguageUtil";
    public static final String ERROR_LABEL = "";
    private static final String DEFAULT_COUNTRY = "US";
    private static final String DEFAULT_LANGUAGE = "en";

    /**
     * 通过color的资源ID获取color对应的数值
     *
     * @param colorResId color resource id defined in values/colors.xml
     * @return
     */
    public static int getColor(@ColorRes int colorResId) {
        Context context = MyApplication.Companion.getInstance();
        return context.getResources().getColor(colorResId);
    }

    /**
     * 通过dimen的资源ID获取对应的数值
     *
     * @param dimenResId dimen resource id defined in values/dimens.xml
     * @return
     */
    public static int getDimen(@DimenRes int dimenResId) {
        Context context = MyApplication.Companion.getInstance();
        return (int) (context.getResources().getDimension(dimenResId) + 0.5);
    }

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
            new int[]{android.R.attr.actionBarSize});
        int height = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return height;
    }

    public static Typeface getFont(@FontRes int fontRes) {
        Context context = MyApplication.Companion.getInstance();
        return ResourcesCompat.getFont(context, fontRes);
    }

    public static Drawable getDrawable(@DrawableRes int drawableRes) {
        return ResourcesCompat.getDrawable(MyApplication.Companion.getInstance().getResources(), drawableRes, null);
    }

    public static Drawable getDrawable(Context context, @DrawableRes int drawableRes) {
        return ResourcesCompat.getDrawable(context.getResources(), drawableRes, null);
    }

    public static String[] getStringArr(@ArrayRes int strArrRes) {
        Context context = MyApplication.Companion.getInstance();
        return context.getResources().getStringArray(strArrRes);
    }

    public static String getString(Context context, @StringRes int strRes) {
        return context.getResources().getString(strRes);
    }

    public static String getString(@StringRes int strRes) {
        Context context = MyApplication.Companion.getInstance();
        return context.getResources().getString(strRes);
    }


    public static String getStringByLocale(int stringId, String language, String country) {
        Resources resources = getApplicationResource(MyApplication.Companion.getInstance().getPackageManager(),
            new Locale(language, country));
        if (resources == null) {
            return ERROR_LABEL;
        }
        try {
            return resources.getString(stringId);
        } catch (Exception e) {
            return ERROR_LABEL;
        }
    }

    public static String getStringToEn(int stringId) {
        return getStringByLocale(stringId, DEFAULT_LANGUAGE, DEFAULT_COUNTRY);
    }

    private static Resources getApplicationResource(PackageManager pm, Locale l) {
        Resources resourceForApplication = null;
        try {
            resourceForApplication = pm.getResourcesForApplication(BuildConfig.APPLICATION_ID);
            updateResource(resourceForApplication, l);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
        return resourceForApplication;
    }

    private static void updateResource(Resources resource, Locale l) {
        Configuration config = resource.getConfiguration();
        config.locale = l;
        resource.updateConfiguration(config, null);
    }

}
