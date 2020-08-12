package com.example.utils.InterfaceImp;

import com.example.utils.LogUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SimpleObserver<T> implements Observer<T> {
    String TAG=this.getClass().getSimpleName();
    @Override
    public void onSubscribe(Disposable d) {
        LogUtil.d(TAG,"onSubscribe");
    }

    @Override
    public void onNext(T o) {
        LogUtil.d(TAG,"onNext");
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.d(TAG,"onError");
    }

    @Override
    public void onComplete() {
        LogUtil.d(TAG,"onComplete");
    }
}
