package com.example.utils;

import android.os.Looper;

public class ThreadUtlis {
    private static class ThreadUtlisHolder{
        private static final ThreadUtlis INSTANCE=new ThreadUtlis();
    }
    private ThreadUtlis(){

    }
    public static final ThreadUtlis getInstance(){
        return  ThreadUtlisHolder.INSTANCE;
    }
    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
