package rxjava.example.v;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import rxjava.example.MainActivity;
import rxjava.example.R;
import rxjava.example.activity.DialogActivity;
import rxjava.example.activity.ObtainPhotoActivity;
import rxjava.example.activity.VoiceActivity;
import rxjava.example.activity.WebViewActivity;
import rxjava.example.base.ProgressDialog;
import rxjava.example.left.LeftMainActivity;
import rxjava.example.p.contract.LoginContract;
import rxjava.example.view.CircleToolbar;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Administrator on 2017/2/22 0022.
 */

public class LoginFragment extends Fragment implements LoginContract.View, View.OnClickListener, View.OnLongClickListener {
    private ProgressDialog dialog;
    private LoginContract.Presenter presenter;
    private ProgressBar progressBar;
    private EditText username;
    private EditText password;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        view.findViewById(R.id.click).setOnClickListener(this);
        view.findViewById(R.id.click).setOnLongClickListener(this);

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    startActivity(new Intent(getActivity(), DialogActivity.class));
            }
        });

        CircleToolbar toolbar = (CircleToolbar) view.findViewById(R.id.ctb_setting_back);
        toolbar.setTitle("主标题");
        Toolbar tl = (Toolbar) view.findViewById(R.id.activity_main_toolbar);
        tl.setTitle("主标题");
        tl.setSubtitle("副标题");
        tl.setTitleTextColor(Color.RED);
        tl.setSubtitleTextColor(Color.WHITE);
        tl.setNavigationIcon(R.mipmap.ic_back);
        tl.setLogo(R.mipmap.ic_launcher);
        tl.inflateMenu(R.menu.menu_main);
        //setSupportActionBar(toolbar);
        tl.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        tl.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_nav_haha:
                        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        getActivity().startActivity(new Intent(getActivity(), VoiceActivity.class));
                        break;
                    case R.id.home_nav_heihei:
                        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        getActivity().startActivity(new Intent(getActivity(), ObtainPhotoActivity.class));
                        break;
                    case R.id.webView:
                        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        getActivity().startActivity(new Intent(getActivity(), WebViewActivity.class));
                        break;
                }
                return true;
            }
        });
        return view;
    }


    public void init() {
        dialog = new ProgressDialog(getActivity());
    }

    @Override
    public void showProgress() {
        dialog.show();
    }

    @Override
    public void dismissProgress() {
        dialog.dismiss();
    }

    @Override
    public void gotoActivity() {
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }


    @Override
    public void onClick(View v) {
        presenter.login(username.getText().toString());
    }

    @Override
    public void onDestroyView() {
        presenter = null;
        super.onDestroyView();
    }


    @Override
    public boolean onLongClick(View view) {
        getActivity().startActivity(new Intent(getActivity(), LeftMainActivity.class));
        return true;
    }
}
