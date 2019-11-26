package com.example.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

// TODO: 2019/11/13 该类待优化
public class RxTimerUtils {
    private List<Disposable> mDisposableList;
    private boolean isDebug=false;
    private String TAG="RxTimerUtils";

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
        this.isDebug=isDebug;
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
     * @param delay 第一次延迟时间
     * @param milliseconds 周期
     * @param next 结果回调
     */
    public void interval(long delay,long milliseconds, final IRxNext next) {
        Observable.interval(delay,milliseconds, TimeUnit.MILLISECONDS)
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
     * 取消订阅
     */
    public void cancelAll() {
        for (Disposable disposable : mDisposableList) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
                Log.d(TAG, "cancelAll");
            }
        }
    }

    public interface IRxNext {
        void doNext(long number);
    }
}