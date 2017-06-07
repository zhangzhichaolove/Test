package rxjava.example.left;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import rxjava.example.R;
import rxjava.example.base.BaseActivity;
import rxjava.example.utils.StatusBarUtils;

/**
 * Created by Administrator on 2017/3/3 0003.
 */

public class ScorllActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sorll);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Toolbar标题");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        CollapsingToolbarLayout.LayoutParams lp = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
        lp.topMargin = StatusBarUtils.getStatusBarHeight();
        toolbar.setLayoutParams(lp);

        ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setHomeButtonEnabled(true); //设置返回键可用
        ab.setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.flbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScorllActivity.this, TabLayoutActivity.class));
            }
        });


//        allBackgroud(this, 0);
        StatusBarUtils.setTranslucent(this);
    }


}
