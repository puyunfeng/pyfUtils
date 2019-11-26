package com.example.utils;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * 震动工具类
 */
public class VibratorUtils {
    private static class VibratorUtilsHolder{
        private static final VibratorUtils INSTANCE=new VibratorUtils();
    }
    private VibratorUtils(){

    }
    public static final VibratorUtils getInstance(){
        return VibratorUtilsHolder.INSTANCE;
    }
    /**
     * 让手机振动milliseconds毫秒
     */
    public  void vibrate(Context context1, long milliseconds) {
        Context context= WeakReferenceUtils.getInstance().getContext(context1);
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        if(vib.hasVibrator()){  //判断手机硬件是否有振动器
            vib.vibrate(milliseconds);
        }
    }


    /**
     * 让手机以我们自己设定的pattern[]模式振动
     * long pattern[] = {1000, 20000, 10000, 10000, 30000};
     */
    public  void vibrate(Context context1, long[] pattern,int repeat){
        Context context= WeakReferenceUtils.getInstance().getContext(context1);
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        if(vib.hasVibrator()){
            vib.vibrate(pattern,repeat);
        }
    }

    /**
     * 取消震动
     */
    public  void virateCancle(Context context1){
        //关闭震动
        Context context= WeakReferenceUtils.getInstance().getContext(context1);
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.cancel();
    }
}
