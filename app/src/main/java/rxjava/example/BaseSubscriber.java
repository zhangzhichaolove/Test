package rxjava.example;

import android.util.Log;

import java.io.IOException;

import rx.Subscriber;

/**
 * 自定义订阅者，并简化回调，只保留：成功/失败
 *
 * @param <T>
 */
public class BaseSubscriber<T> extends Subscriber<T> {

    protected SubscriberOnNextListener subscriberOnNextListener;

    public BaseSubscriber(SubscriberOnNextListener subscriberOnNextListener) {
        this.subscriberOnNextListener = subscriberOnNextListener;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        Log.e(this.getClass().getSimpleName(), e.getMessage());
        if (e instanceof RuntimeException) {
            subscriberOnNextListener.onError(0);
        } else if (e instanceof IOException) {
            subscriberOnNextListener.onError(1);
        } else if (e instanceof Exception) {
            subscriberOnNextListener.onError(2);
        }
    }

    @Override
    public void onNext(T t) {
        subscriberOnNextListener.onNext(t);
    }
}