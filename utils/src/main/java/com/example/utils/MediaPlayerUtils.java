package com.example.utils;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

public class MediaPlayerUtils {

    private Ringtone r;

    private static class ObjectUtilsHolder{
        private static final MediaPlayerUtils INSTANCE=new MediaPlayerUtils();
    }
    private MediaPlayerUtils(){

    }
    public static final MediaPlayerUtils getInstance(){
        return ObjectUtilsHolder.INSTANCE;
    }
    /**
     * 播放系统默认提示音
     *
     * @return MediaPlayer对象
     *
     * @throws Exception
     */
    public void defaultMediaPlayer(Context mContext1) throws Exception {
        Context mContext=  WeakReferenceUtils.getInstance().getContext(mContext1);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        r = RingtoneManager.getRingtone(mContext, notification);
        r.play();
    }
    /**
     * 播放系统默认来电铃声
     *
     * @return MediaPlayer对象
     *
     * @throws Exception
     */
    public void defaultCallMediaPlayer(Context mContext1){
        Context mContext=  WeakReferenceUtils.getInstance().getContext(mContext1);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        r = RingtoneManager.getRingtone(mContext, notification);
        r.play();
    }
    /**
     * 播放系统默认闹钟铃声
     *
     * @return MediaPlayer对象
     *
     * @throws Exception
     */
    public void defaultAlarmMediaPlayer(Context mContext1) throws Exception {
        Context mContext=  WeakReferenceUtils.getInstance().getContext(mContext1);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(mContext, notification);
        r.play();
    }
    public void stopPlay(){
        if (r!=null&&r.isPlaying()) {
            r.stop();
        }
    }
}
