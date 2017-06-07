package rxjava.example;

/**
 * Rxjava回调接口
 * @param <T>
 */
public interface SubscriberOnNextListener<T>{
    void onError(int errorCode);
    void onNext(T t);
}