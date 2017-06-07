package rxjava.example.p;

import android.util.Log;

import rxjava.example.BaseDataEntity;
import rxjava.example.BaseSubscriber;
import rxjava.example.SubscriberOnNextListener;
import rxjava.example.activity.LoginController;
import rxjava.example.p.contract.LoginContract;

/**
 * Class Note:登陆的Presenter 的接口，实现类为LoginPresenterImpl，完成登陆的验证，以及销毁当前view
 */
public class LoginPresenter implements LoginContract.Presenter {
    private final String TAG = this.getClass().getSimpleName();
    LoginContract.View view;
    LoginController loginController;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        view.setPresenter(this);
        this.loginController = new LoginController();
    }

    @Override
    public void login(String msg) {
        view.showProgress();
        loginController.chat(new BaseSubscriber<BaseDataEntity>(new SubscriberOnNextListener<BaseDataEntity>() {
            @Override
            public void onError(int errorCode) {
                view.dismissProgress();
                Log.e(TAG, "发送失败");
            }

            @Override
            public void onNext(BaseDataEntity o) {
                // BaseDataEntity dataEntity = (BaseDataEntity) o;
                view.dismissProgress();
                view.gotoActivity();
                Log.e(TAG, "发送成功:" + o);
                Log.e(TAG, "解析数据:" + o.getContent());
            }
        }), msg);
    }

    @Override
    public void start() {

    }
}