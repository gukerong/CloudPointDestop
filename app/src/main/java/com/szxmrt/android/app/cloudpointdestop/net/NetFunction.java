package com.szxmrt.android.app.cloudpointdestop.net;

import com.szxmrt.android.app.cloudpointdestop.LogUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class NetFunction implements Function<Observable<Throwable>, ObservableSource<?>> {

    private int mCount = 0;

    @Override
    public ObservableSource<?> apply(Observable<Throwable> observable) throws Exception {
        return observable.flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
            if (mCount < 4) {
                mCount++;
	            LogUtil.e("retry",mCount);
                return Observable.timer(1000, TimeUnit.MILLISECONDS);
            }
            return Observable.error(throwable);
        });
    }
}