package com.example.utils;

import android.util.Log;

import com.example.utils.InterfaceImp.SimpleObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

// TODO: 2019/11/13 该类待优化
public class RxTimerUtils {

    private String TAG =this.getClass().getSimpleName();

    private static class RxTimerUtilsHolder {
        private static final RxTimerUtils INSTANCE = new RxTimerUtils();
    }
    private RxTimerUtils() {
    }

    public static final RxTimerUtils getInstance() {
        return RxTimerUtilsHolder.INSTANCE;
    }

    HashMap<String, Disposable> hashMap = new HashMap<>();

    /**
     * 间隔时间执行某个操作
     * @param delay 延迟执行的时间 毫秒
     * @param period 周期 毫秒
     * @param next 回调函数
     * @param mTag 唯一标识
     */
    public void interval(long delay,long period, final IRxNext next, final String mTag) {
        Observable.interval(delay,period, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        super.onSubscribe(disposable);
                        cancePre(mTag);
                        if(!disposable.isDisposed()){
                            hashMap.put(mTag, disposable);
                        }else{
                            LogUtil.d(TAG,"disposable被取消");
                        }
                    }
                    @Override
                    public void onNext(@NonNull Long number) {
                        super.onNext(number);
                        if (next != null) {
                            next.doNext(number);
                        }
                    }
                });
    }

    /**
     * 取消之前未被关闭掉的定时器
     * @param mTag
     */
    private void cancePre(String mTag) {
        Disposable pre= hashMap.get(mTag);
        if(pre!=null&&!pre.isDisposed()){
            pre.dispose();
        }
    }

    /**
     * 延迟执行一次
     * @param delay 延迟时间 ms
     * @param iRxNext 回调
     * @param mTag 唯一标识
     */
    public void onlyOneDelay(long delay, final IRxNext iRxNext, final String mTag) {
        Observable.just("onlyOne")
                .delay(delay, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<String>(){
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        super.onSubscribe(disposable);
                        cancePre(mTag);
                        if(!disposable.isDisposed()){
                            hashMap.put(mTag, disposable);
                        }
                    }
                    @Override
                    public void onNext(String s) {
                        super.onNext(s);
                        if(iRxNext!=null){
                            iRxNext.doNext(1);
                        }
                    }
                });
    }
    /**
     * 取消订阅
     */
    public void cancelAll() {
        try {
            if (hashMap.keySet() != null) {
                for (String key : hashMap.keySet()) {
                    Disposable disposable = hashMap.get(key);
                    if (!disposable.isDisposed()) {
                        disposable.dispose();
                    }
                    hashMap.remove(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消指定订阅
     */
    public void cancelTarget(String mTag) {
        try {
            if (hashMap.get(mTag) != null) {
                Disposable disposable = hashMap.get(mTag);
                if (!disposable.isDisposed()) {
                    disposable.dispose();
                }
                Log.d(TAG, "cancel" + mTag);
                hashMap.remove(mTag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public interface IRxNext {
        void doNext(long number);
    }
    // TODO: 2019/11/26 执行次数限制
}
