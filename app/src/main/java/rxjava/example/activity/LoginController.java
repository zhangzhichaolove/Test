package rxjava.example.activity;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rxjava.example.ApiExecutor;
import rxjava.example.BaseDataEntity;

public class LoginController {

    ApiExecutor apiExecutor;

    public LoginController() {
        apiExecutor = ApiExecutor.getInstance();
    }


    public void chat(Subscriber<BaseDataEntity> subscriber, String msg) {
//        Observable observable = apiExecutor.getApiService().chat("free", "0", msg);
//        apiExecutor.toSubscribe(observable, subscriber);
        Observable observable = apiExecutor.getApiService().chat("api.php?key=free&appid=0&msg=" + msg);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}