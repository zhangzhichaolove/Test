package rxjava.example.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import rxjava.example.R;
import rxjava.example.v.DialogFragment;

/**
 * Created by Chao on 2017/3/13.
 */

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mvp_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(DialogFragment.class.getName());
        if (fragment == null) {
            fragment = DialogFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.login_fl_cotent, fragment, fragment.getClass().getName()).commit();
        }
        //new LoginPresenter(fragment);
    }
}
