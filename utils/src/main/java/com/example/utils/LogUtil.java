package com.example.utils;

import android.util.Log;


public class LogUtil {
    private final static boolean isDebug=true;

    public static void i(String TAG, String msg){
        if (isDebug){
            Log.i(TAG,msg);
        }
    }

    public static void d(String TAG, String msg){
        if (isDebug){
            Log.d(TAG,msg);
        }
    }
    public static void d(FlagBean mBean, String msg){
        if (isDebug){
            if(mBean.getShow()){
                Log.d(mBean.getmTag(),msg);
            }
        }
    }
    public static void e(String TAG, String msg){
        if (isDebug){
            Log.d(TAG,msg);
        }
    }
    public static void e(Exception e){
        if (isDebug){
            e.printStackTrace();
        }
    }
    public static class FlagBean {
        private String mTag;
        private Boolean isShow;
        public FlagBean(String mTag, Boolean isShow) {
            this.mTag=mTag;
            this.isShow=isShow;
        }

        public String getmTag() {
            return mTag;
        }

        public void setmTag(String mTag) {
            this.mTag = mTag;
        }

        public Boolean getShow() {
            return isShow;
        }

        public void setShow(Boolean show) {
            isShow = show;
        }
    }
}
