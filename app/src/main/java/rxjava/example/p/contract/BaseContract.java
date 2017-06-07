package rxjava.example.p.contract;

import rxjava.example.base.BasePresenter;
import rxjava.example.base.BaseView;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public interface BaseContract {

    interface View extends BaseView<Presenter> {
        String getTitle();

        void showDialog();

        void gotoActivity();
    }

    interface Presenter extends BasePresenter {
        //需要处理的逻辑，逻辑处理完成调用View更新Ui
        void login();

        void finsh();
    }
}
