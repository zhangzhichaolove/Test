package rxjava.example.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import rxjava.example.R;
import rxjava.example.base.RootView;
import rxjava.example.p.contract.BaseContract;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class BaseActivityView extends RootView<BaseContract.Presenter> implements BaseContract.View {

    public BaseActivityView(Context context) {
        super(context);
    }

    public BaseActivityView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_base_view, this);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initAction() {
        mPresenter.start();
        mPresenter.login();
        mPresenter.finsh();
    }


    @Override //实现View方法
    public String getTitle() {
        return this.getClass().getName();
    }

    @Override
    public void showDialog() {
        Log.e("TAG", "showDialog");
    }

    @Override
    public void gotoActivity() {
        Log.e("TAG", "gotoActivity");
    }


//    @Override
//    public void setPresenter(BaseContract.Presenter presenter) {
//        mPresenter = presenter;
//    }
}
