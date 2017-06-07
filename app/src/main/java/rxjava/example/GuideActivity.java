package rxjava.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.nineoldandroids.animation.ObjectAnimator;

import rxjava.example.activity.PermissionActivity;
import rxjava.example.base.BaseActivity;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class GuideActivity extends BaseActivity {
    KenBurnsView mBgImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Uri photoURI = FileProvider.getUriForFile(this,
//                BuildConfig.APPLICATION_ID + ".provider", new File("path"));
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        mBgImg = (KenBurnsView) findViewById(R.id.splash_bg_img);
        //Glide.with(this).load(R.drawable.pic_cinema).into(mBgImg);
        mBgImg.setImageResource(R.drawable.pic_cinema);
        TextView loding = (TextView) findViewById(R.id.loding);
        Button welcome = (Button) findViewById(R.id.welcome);
        loding.setVisibility(View.VISIBLE);
        //.setInterpolator(new LinearInterpolator())
        ObjectAnimator//
                .ofFloat(loding, "scaleY", 0.0F, 1.0F)//
                .setDuration(5000)//
                .start();
        ObjectAnimator//
                .ofFloat(welcome, "scaleX", 0.0F, 1)//
                .setDuration(5000)//
                .start();
        ObjectAnimator//
                .ofFloat(welcome, "scaleY", 0.0F, 1)//
                .setDuration(5000)//
                .start();
        ObjectAnimator//
                .ofFloat(welcome, "translationY", -200.0F, 0)//
                .setDuration(5000)//
                .start();
        welcome.setAlpha(0);
        welcome.animate().alpha(1).setDuration(5000).setInterpolator(new LinearInterpolator())./*.setListener()*/start();
        welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
            }
        });
        loding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GuideActivity.this, PermissionActivity.class));
            }
        });
        //mBgImg.restart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBgImg.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBgImg.resume();
    }
}
