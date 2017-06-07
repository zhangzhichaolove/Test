package rxjava.example.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * 状态栏工具类
 * Created by Administrator on 2017/3/6 0006.
 */

public class StatusBarUtils {
    /**
     * 全透明方案（无阴影）
     *
     * @param activity
     */
    public static void setTranslucent(Activity activity) {
        setTranslucent(activity, false);
    }

    /**
     * 设置全透明方案（不预留状态栏位置,不修改底部导航栏）
     *
     * @param activity      Activity
     * @param istranslucent 手动设置半透明表标识
     */
    public static void setTranslucent(Activity activity, boolean istranslucent) {
        //SDK大于4.4并且小于5.0 开启 4.4状态栏透明（有浅色阴影）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP || (istranslucent && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(false);//不预留状态栏空间
            rootView.setClipToPadding(false);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//SDK大于5.0开启 5.0状态栏透明(全透明)
            if (istranslucent) return;//如果采用半透明方式，则不设置全透明。
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    /*| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION*/);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                   /* | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION*/
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 根据系统版本设置状态栏颜色，SDK版本大于4.4 API-19 开始支持
     *
     * @param activity
     * @param statusColor
     */
    public static void setStatusColor(Activity activity, int statusColor) {
        if (statusColor != -1) {//颜色有效时，才设置。
            //版本介于4.4-5.0时候，设置状态栏颜色。
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
                View statusBarView = new View(activity);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        getStatusBarHeight());
                statusBarView.setBackgroundColor(statusColor);
                contentView.addView(statusBarView, lp);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//大于5.0时，直接设置状态栏颜色
                activity.getWindow().setStatusBarColor(statusColor);
                activity.getWindow().setNavigationBarColor(statusColor);
            }
        }
    }

    /**
     * 获取状态栏高度
     *
     * @return 状态栏高度
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = Resources.getSystem().getDimensionPixelSize(resourceId);
        }
        return result;
    }


}
