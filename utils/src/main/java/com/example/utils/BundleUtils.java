package com.example.utils;

import android.app.Activity;
import android.os.Bundle;


public class BundleUtils {

    private static class BundleUtilsHolder {
        private static final BundleUtils INSTANCE = new BundleUtils();
    }

    private BundleUtils() {

    }

    public static final BundleUtils getInstance() {
        return BundleUtilsHolder.INSTANCE;
    }

    public Object getSerializable(Activity mActivity, String key) {
        Bundle bundle = mActivity.getIntent().getExtras();
        return  bundle.getSerializable(key);
    }
    public Object getParcelableArrayList(Activity mActivity, String key) {
        Bundle bundle = mActivity.getIntent().getExtras();
        return  bundle.getParcelableArrayList(key);
    }
    public Object getParcelable(Activity mActivity, String key) {
        Bundle bundle = mActivity.getIntent().getExtras();
        return  bundle.getParcelable(key);
    }
    public int getInt(Activity mActivity, String key) {
        Bundle bundle = mActivity.getIntent().getExtras();
        return  bundle.getInt(key);
    }
    public String getString(Activity mActivity, String key) {
        Bundle bundle = mActivity.getIntent().getExtras();
        return  bundle.getString(key);
    }
}
