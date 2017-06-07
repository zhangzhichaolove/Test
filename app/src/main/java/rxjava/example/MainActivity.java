package rxjava.example;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rxjava.example.activity.LoginActivity;
import rxjava.example.base.BaseActivity;
import rxjava.example.view.VoiceDialogManager;

public class MainActivity extends BaseActivity {

    private String TAG = this.getClass().getSimpleName();
    private ImageView imageView;
    VoiceDialogManager dialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.main_image);
        dialogManager = new VoiceDialogManager(this);
        //创建 Observable 即被观察者
        Observable<String> sender = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hi，Weavey！");  //发送数据"Hi，Weavey！"
                subscriber.onNext("create1"); //发射一个"create1"的String
                subscriber.onNext("create2"); //发射一个"create2"的String
                subscriber.onCompleted();//发射完成,这种方法需要手动调用onCompleted，才会回调Observer的onCompleted方法
            }
        }).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s.toString();
            }
        }).subscribeOn(Schedulers.io())//订阅所在线程;
                .unsubscribeOn(Schedulers.io())//退订所在线程;
                .observeOn(AndroidSchedulers.mainThread());//观察所在线程;
        //Observer<String> receiver = new Observer<String>() {};
        //创建 Observer 即观察者
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                //正常接收数据调用
                Log.e(TAG, "onNext: " + s);
                Thread thread = Thread.currentThread();
                Log.e(TAG, "当前所在线程：" + thread.getId() + ":" + thread.getName());
            }

            @Override
            public void onCompleted() {
                //数据接收完成时调用
                Log.e(TAG, "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                //发生错误调用
                Log.e(TAG, "Error!");
            }

            @Override
            public void onStart() {
                super.onStart();
                Thread thread = Thread.currentThread();
                Log.e(TAG, "当前所在线程：" + thread.getId() + ":" + thread.getName());
                Log.e(TAG, "这是 Subscriber 增加的方法。在 subscribe 刚开始调用");
                //用于取消订阅。在这个方法被调用后，Subscriber 将不再接收事件。
                //unsubscribe();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        startActivity(new Intent(PermissionActivity.this, LoginActivity.class));
//                        finish();
//                    }
//                }).start();

            }
        };
        //创建了 Observable 和 Observer 之后，再用 subscribe() 方法将它们联结起来，整条链子就可以工作了。
        //sender.subscribe(subscriber).unsubscribe();


        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                try {
                    Thread.sleep(5000);//模拟加载图片，在IO线程。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Drawable drawable = getDrawable(R.mipmap.ic_launcher);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onNext(Drawable drawable) {
                        imageView.setImageDrawable(drawable);
                    }

                    @Override
                    public void onCompleted() {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        dialogManager.dismiss();
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
