package rxjava.example.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import rxjava.example.R;
import rxjava.example.base.BaseActivity;
import rxjava.example.p.CameraPresenter;
import rxjava.example.v.CameraFragment;

/**
 * Created by Administrator on 2017/3/1 0001.
 */

public class CameraActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mvp_activity);
//        CircleToolbar toolbar = (CircleToolbar) findViewById(R.id.ctb_setting_back);
//        toolbar.setTitle("Title");
//        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        CameraFragment fragment = (CameraFragment) fragmentManager.findFragmentByTag(CameraFragment.class.getName());
        if (fragment == null) {
            fragment = CameraFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.login_fl_cotent, fragment, fragment.getClass().getName()).commit();
        }
        new CameraPresenter(fragment);
    }
}
