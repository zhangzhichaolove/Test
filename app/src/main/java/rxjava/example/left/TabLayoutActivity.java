package rxjava.example.left;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rxjava.example.R;
import rxjava.example.base.BaseActivity;

/**
 * Created by Administrator on 2017/3/3 0003.
 */

public class TabLayoutActivity extends BaseActivity {

    private TabLayout mTablayout;
    private ViewPager mViewPager;

    private TabLayout.Tab one;
    private Toolbar toolbar;

    MyAdapter adapter;

    private int[] images = {R.drawable.ic_menu, R.mipmap.ic_launcher, R.drawable.voice1,
            R.drawable.voice2, R.drawable.voice3, R.mipmap.adj, R.mipmap.icon, R.mipmap.ic_back};
    private int[] selectImage = {R.mipmap.adj, R.mipmap.icon, R.mipmap.ic_back, R.mipmap.v1, R.mipmap.v2,
            R.drawable.ic_menu, R.mipmap.ic_launcher, R.drawable.voice1};

    private String[] mTitles = new String[]{"第一个", "第二个", "第三个", "第四个", "第五个", "第六个", "第七个", "第八个"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_back);
        ab.setHomeButtonEnabled(true); //设置返回键可用
        ab.setDisplayHomeAsUpEnabled(true);

        initViews();
        initEvents();
        ab.setTitle(mTitles[0]);
        //toolbar.setTitle(mTitles[0]);
    }


    private void initEvents() {

        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {//当前点击的tab选项回调
                //mViewPager.setCurrentItem(tab.getPosition());
                toolbar.setTitle(tab.getText());
                //clear();
                //tab.setIcon(R.drawable.ic_menu);
                TextView tv = (TextView) tab.getCustomView().findViewById(R.id.textView);
                ImageView img = (ImageView) tab.getCustomView().findViewById(R.id.imageView);
                tv.setTextColor(Color.RED);
                img.setImageResource(selectImage[tab.getPosition()]);
            }

            private void clear() {
                for (int i = 0; i < mTablayout.getTabCount(); i++) {
                    int index = i % images.length;
                    mTablayout.getTabAt(i).setIcon(images[index]);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {//上一次的tab回调
                TextView tv = (TextView) tab.getCustomView().findViewById(R.id.textView);
                ImageView img = (ImageView) tab.getCustomView().findViewById(R.id.imageView);
                tv.setTextColor(Color.WHITE);
                img.setImageResource(images[tab.getPosition()]);
                //tab.setIcon(R.mipmap.ic_launcher);
                //mTablayout.getTabAt(tab.getPosition()).setIcon(ContextCompat.getDrawable(TabLayoutActivity.this, R.mipmap.ic_launcher));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {//// 再次点击同一个tab的回调
            }
        });

    }

    private void initViews() {
        mTablayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new MyAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(adapter);

        mTablayout.setupWithViewPager(mViewPager);
//        mTablayout.setTabsFromPagerAdapter(adapter);

//        for (int i = 0; i < 10; i++) {
//            mTablayout.getTabAt(i).setIcon(R.mipmap.ic_launcher);
//        }

        for (int i = 0; i < mTablayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTablayout.getTabAt(i);
            tab.setCustomView(getTabView(i));
        }

        //mTablayout.setScrollPosition(0, 0.5F, false);

        mViewPager.setCurrentItem(0);
        //TabLaout.TabLayoutOnPageChangeLisetener继承自OnPageChangeListener
        TabLayout.TabLayoutOnPageChangeListener listener =
                new TabLayout.TabLayoutOnPageChangeListener(mTablayout);
        mViewPager.addOnPageChangeListener(listener);
        //设置缓存数量
        //mViewPager.setOffscreenPageLimit(5);

    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(mTitles[position]);
        tv.setTextColor(Color.WHITE);
        ImageView img = (ImageView) view.findViewById(R.id.imageView);
        img.setImageResource(images[position]);
        return view;
    }

    class MyAdapter extends FragmentPagerAdapter {
        private Context context;

        public MyAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return OneFragment.newInstance();
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
//            return null;
        }
    }
}
