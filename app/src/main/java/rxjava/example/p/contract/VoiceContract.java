package rxjava.example.p.contract;

import rxjava.example.base.BasePresenter;
import rxjava.example.base.BaseView;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public interface VoiceContract {
    interface View extends BaseView<Presenter> {

        void showDialog();

        void showCancelDialog();

        void showShortDialog();

        void dismissDialog();

        void levelDialog(int level);

    }

    interface Presenter extends BasePresenter {
    }
}
