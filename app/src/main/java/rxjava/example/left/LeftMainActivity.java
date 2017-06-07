package rxjava.example.left;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.nineoldandroids.view.ViewHelper;

import rxjava.example.R;
import rxjava.example.activity.ActivityBase;
import rxjava.example.base.BaseActivity;

public class LeftMainActivity extends BaseActivity {
    //声明相关变量
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView lvLeftMenu;
    private String[] lvs = {"List Item 01", "List Item 02", "List Item 03", "List Item 04"};
    private ArrayAdapter arrayAdapter;
    private ImageView ivRunningMan;
    private AnimationDrawable mAnimationDrawable;

    private TextInputLayout textInputLayout;

    private FloatingActionButton flbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerlayout_activity);
        findViews(); //获取控件
        //京东RunningMan动画效果，和本次Toolbar无关
        mAnimationDrawable = (AnimationDrawable) ivRunningMan.getBackground();
        mAnimationDrawable.start();
        toolbar.setTitle("Toolbar");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mAnimationDrawable.stop();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mAnimationDrawable.start();
            }
        };

        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //设置菜单列表
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lvs);
        lvLeftMenu.setAdapter(arrayAdapter);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);//设置取消阴影
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mDrawerLayout.getChildAt(0);//返回抽屉布局中的索引为0的子view
                View mMenu = drawerView;//侧面菜单
                float scale = 1 - slideOffset;//偏移量导致scale从1.0-0.0
                float rightScale = 0.8f + scale * 0.2f;//将内容区域从1.0-0.0转化为1.0-0.8

                if (drawerView.getTag().equals("LEFT")) {

                    float leftScale = 1 - 0.3f * scale;//0.7-1.0
                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    //ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));//开始这里设置成了这样，导致背景透明度有1.0-0.6
                    //ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * scale);
                    ViewHelper.setTranslationX(mContent,
                            mMenu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                } else {
                    ViewHelper.setTranslationX(mContent,
                            -mMenu.getMeasuredWidth() * slideOffset);
                    //设置大小变化的中心
                    ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        textInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout);
        //检测长度应该低于6位数
        textInputLayout.getEditText().addTextChangedListener(new MinLengthTextWatcher(textInputLayout, "长度低于6位数!"));
        //开启计数
        textInputLayout.setCounterEnabled(true);
        textInputLayout.setCounterMaxLength(10);//最大输入限制数
        //TODO 让所有的图片显示他本身的颜色.
        NavigationView navigationView = (NavigationView) findViewById(R.id.id_nv_menu);
        //navigationView.setItemIconTintList(null);
        //navigationView.setNavigationItemSelectedListener();//监听

        flbtn = (FloatingActionButton) findViewById(R.id.flbtn);
        final CoordinatorLayout layout = (CoordinatorLayout) findViewById(R.id.coordlayout);
        flbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(layout, "测试", Snackbar.LENGTH_SHORT).setAction("跳转", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(LeftMainActivity.this, ActivityBase.class));
                    }
                }).show();
            }
        });
        ivRunningMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LeftMainActivity.this, ScorllActivity.class));
            }
        });
    }

    /**
     * 隐藏键盘
     */
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 文本监听
     */
    class MinLengthTextWatcher implements TextWatcher {
        private String errorStr;
        private TextInputLayout textInputLayout;

        public MinLengthTextWatcher(TextInputLayout textInputLayout, String errorStr) {
            this.textInputLayout = textInputLayout;
            this.errorStr = errorStr;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            // 文字改变后回调
            if (textInputLayout.getEditText().getText().toString().length() <= 6) {
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError(errorStr);
            } else {
                textInputLayout.setErrorEnabled(false);
                hideKeyboard();
            }
        }
    }

    private void findViews() {
        ivRunningMan = (ImageView) findViewById(R.id.iv_main);
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        lvLeftMenu = (ListView) findViewById(R.id.lv_left_menu);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
            mDrawerLayout.closeDrawer(GravityCompat.END);
            //mDrawerLayout.openDrawer(Gravity.RIGHT);
        } else
            super.onBackPressed();
    }
}