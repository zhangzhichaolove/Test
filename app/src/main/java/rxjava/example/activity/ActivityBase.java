package rxjava.example.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import rxjava.example.R;
import rxjava.example.base.BaseActivity;
import rxjava.example.p.BaseActivityPresenter;
import rxjava.example.view.BaseActivityView;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class ActivityBase extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //开始加载视图，这时初始化BaseActivityView
        setContentView(R.layout.activity_base);
        BaseActivityView view = (BaseActivityView) findViewById(R.id.main_view);
        mPresenter = new BaseActivityPresenter(view);
    }
}
