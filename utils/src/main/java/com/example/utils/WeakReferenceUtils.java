package com.example.utils;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class WeakReferenceUtils {
    private  WeakReference<Context> reference=null;
    private  WeakReference<Activity> reference_a=null;

    private static class ObjectUtilsHolder{
        private static final WeakReferenceUtils INSTANCE=new WeakReferenceUtils();
    }
    private WeakReferenceUtils(){

    }
    public static final WeakReferenceUtils getInstance(){
        return ObjectUtilsHolder.INSTANCE;
    }
    public void setContext(Context context){
        reference = new WeakReference<>(context);
    }
    public Context getContext(Context context){
       return reference.get();
    }
    public void setContext(Activity mActivity){
        reference_a = new WeakReference<>(mActivity);
    }
    public Activity getContext(Activity mActivity){
        return reference_a.get();
    }
}
