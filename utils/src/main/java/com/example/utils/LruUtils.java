package com.example.utils;

import android.content.Context;

import java.util.HashMap;

public class LruUtils {
    private static HashMap<String, String> mDataHash = new HashMap<>();

    private static class UserAccountUtilsHolder {
        private static final LruUtils INSTANCE = new LruUtils();
    }

    private LruUtils() {

    }

    public static final LruUtils getInstance() {
        return UserAccountUtilsHolder.INSTANCE;
    }

    /**
     *
     * @param context
     * @param key
     * @param value
     */
    // TODO: 2019/11/15 采用Lru算法优化
    public void saveData(Context context, String key, String value) {
        //保存于内存中
        mDataHash.put(key, value);
        //保存于本地
        SpUtils.getInstance().putString(context, key, value);
    }

    public String getData(Context context, String key) {
        //取于内存中
        if (mDataHash.get(key) == null) {
            //取于本地
            return SpUtils.getInstance().getString(context, key, null);
        } else {
            return mDataHash.get(key);
        }
    }
}
