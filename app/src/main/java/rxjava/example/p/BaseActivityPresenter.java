package rxjava.example.p;

import android.text.TextUtils;
import android.util.Log;

import rxjava.example.base.BasePresenterImpl;
import rxjava.example.base.BaseView;
import rxjava.example.p.contract.BaseContract;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class BaseActivityPresenter extends BasePresenterImpl<BaseContract.View> implements BaseContract.Presenter {
//    BaseContract.View view;

    public BaseActivityPresenter(BaseView view) {
        super(view);
    }

//    public BaseActivityPresenter(BaseContract.View view) {
//        this.view = view;
//        view.setPresenter(this);
//    }

    @Override
    public void start() {
        Log.e("TAG", "start");
    }


    @Override
    public void login() {
        view.showDialog();
        if (!TextUtils.isEmpty(view.getTitle())) {
            Log.e("TAG", "接收到数据:" + view.getTitle() + "，开始执行跳转逻辑。");
            view.gotoActivity();
        }
    }

    @Override
    public void finsh() {
        Log.e("TAG", "finsh");
    }
}
