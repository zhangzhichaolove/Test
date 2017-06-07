package rxjava.example.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import rxjava.example.R;
import rxjava.example.base.BaseActivity;
import rxjava.example.p.LoginPresenter;
import rxjava.example.utils.PermissionHelp;
import rxjava.example.v.LoginFragment;


/**
 * Class Note:MVP模式中View层对应一个activity，这里是登陆的activity
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mvp_activity);
//        CircleToolbar toolbar = (CircleToolbar) findViewById(R.id.ctb_setting_back);
//        toolbar.setTitle("Title");
//        setSupportActionBar(toolbar);

        PermissionHelp.sendApply(this, new PermissionHelp.PermissionsResult() {
                    @Override
                    public void permissionsResult(boolean Result) {
                        Log.e("TAG", Result + "");

                    }
                }, true, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        LoginFragment fragment = (LoginFragment) fragmentManager.findFragmentByTag(LoginFragment.class.getName());
        if (fragment == null) {
            fragment = LoginFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.login_fl_cotent, fragment, fragment.getClass().getName()).commit();
        }
        new LoginPresenter(fragment);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionHelp.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        PermissionHelp.onActivityResult(requestCode, resultCode, data);
    }

}