package com.example.utils;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class WeakReferenceUtils {
    private WeakReference<Context> reference = null;
    private WeakReference<Activity> reference_a = null;
    private WeakReference<AppCompatActivity> referenceAppCompat = null;


    private static class ObjectUtilsHolder {
        private static final WeakReferenceUtils INSTANCE = new WeakReferenceUtils();
    }

    private WeakReferenceUtils() {

    }

    public static final WeakReferenceUtils getInstance() {
        return ObjectUtilsHolder.INSTANCE;
    }

    public Context getContext(Context context) {
        reference = new WeakReference<>(context);
        return reference.get();
    }

    public Activity getContext(Activity mActivity) {
        reference_a = new WeakReference<>(mActivity);
        return reference_a.get();
    }
    public AppCompatActivity getContext(AppCompatActivity mActivity) {
        referenceAppCompat = new WeakReference<>(mActivity);
        return referenceAppCompat.get();
    }
}
