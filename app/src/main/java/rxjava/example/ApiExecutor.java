package rxjava.example;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rxjava.example.m.ApiService;

public class ApiExecutor {

    private ApiService apiService;

    /**
     * 私有构造方法
     */
    private ApiExecutor() {
    }

    /**
     * 使用静态类创建单例
     */
    private static class SingletonHolder {
        private static final ApiExecutor INSTANCE = new ApiExecutor();
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static ApiExecutor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取ApiService
     *
     * @return
     */
    public ApiService getApiService() {
        if (apiService == null) {
            apiService = ApiService.Factory.createApiService();
        }
        return apiService;
    }

    /**
     * 使用RxJava异步执行网络请求
     *
     * @param observable
     * @param subscriber
     * @param <T>
     * @param <R>
     */
    public <T extends Mapper<R>, R> void toSubscribe(Observable<T> observable, Subscriber<R> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<T, R>() {
                    @Override
                    public R call(T t) {
                        return t.transform();
                    }
                })
                .subscribe(subscriber);
    }
}