package rxjava.example.p.contract;

import rxjava.example.base.BasePresenter;
import rxjava.example.base.BaseView;

/**
 * Created by Administrator on 2017/2/22 0022.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void showProgress();

        void dismissProgress();

        void gotoActivity();
    }

    interface Presenter extends BasePresenter {
        void login(String msg);
    }

}
