package rxjava.example.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import rxjava.example.R;
import rxjava.example.base.BaseActivity;
import rxjava.example.v.VoiceFragment;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public class VoiceActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mvp_activity);
        FragmentManager fragmentManager = getSupportFragmentManager();
        VoiceFragment fragment = (VoiceFragment) fragmentManager.findFragmentByTag(VoiceFragment.class.getName());
        if (fragment == null) {
            fragment = VoiceFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.login_fl_cotent, fragment, fragment.getClass().getName()).commit();
        }
        //new VoicePresenter(fragment);
    }
}
