package com.example.utils;

import android.util.Log;

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
    private List<Disposable> mDisposableList;
    private boolean isDebug = false;
    private String TAG = "RxTimerUtils";

    private static class RxTimerUtilsHolder {
        private static final RxTimerUtils INSTANCE = new RxTimerUtils();
    }

    private RxTimerUtils() {
        mDisposableList = new ArrayList<>();
    }

    public static final RxTimerUtils getInstance() {
        return RxTimerUtilsHolder.INSTANCE;
    }


    public void setDebug(Boolean isDebug) {
        this.isDebug = isDebug;
    }

    /**
     * 每隔milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public void interval(long milliseconds, final IRxNext next) {
        Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        Log.d(TAG, "onSubscribe: ");
                        mDisposableList.add(disposable);
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 每隔milliseconds毫秒后执行next操作
     *
     * @param delay        第一次延迟时间
     * @param milliseconds 周期
     * @param next         结果回调
     */
    public void interval(long delay, long milliseconds, final IRxNext next) {
        Observable.interval(delay, milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        Log.d(TAG, "onSubscribe: ");
                        mDisposableList.add(disposable);
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    HashMap<String, Disposable> hashMap = new HashMap<>();

    public void interval(long milliseconds, final IRxNext next, final String mTag) {
        Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        Log.d(TAG, "onSubscribe: ");
                        hashMap.put(mTag, disposable);
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 取消订阅
     */
    public void cancelAll() {
        try {
            for (Disposable disposable : mDisposableList) {
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                    Log.d(TAG, "cancelAll");
                }
            }
            mDisposableList.clear();
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

    /**
     * 延迟执行一次
     * @param des 传递的描述信息
     * @param s 秒
     */
    public void delay(String des, int s, final IRxNext iRxNext, final String mTag) {
        Observable.just(des)
                //延时两秒，第一个参数是数值，第二个参数是事件单位
                .delay(s, TimeUnit.SECONDS)
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>(){

                    @Override
                    public void onSubscribe(Disposable d) {
                        hashMap.put(mTag, d);
                    }

                    @Override
                    public void onNext(String s) {
                        iRxNext.doNext(1);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface IRxNext {
        void doNext(long number);
    }
    // TODO: 2019/11/26 执行次数限制
}
