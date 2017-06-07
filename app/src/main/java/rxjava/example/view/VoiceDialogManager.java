package rxjava.example.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import rxjava.example.R;

/**
 * 语音Dialog  管理器
 * Created by Administrator on 2017/2/28 0028.
 */

public class VoiceDialogManager {

    private Dialog dialog;
    private Context context;
    private ImageView dialog_iv_icon;
    private AnimationDrawable anim;

    public VoiceDialogManager(Context context) {
        this.context = context;
        init();
    }

    /**
     * Dialog初始化方法
     */
    private void init() {
        dialog = new Dialog(context, R.style.DialogThemes);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_voice_manager, null);
        dialog.show();
        dialog.setContentView(inflate);
        dialog_iv_icon = (ImageView) dialog.findViewById(R.id.dialog_iv_icon);
        dialog_iv_icon.setBackgroundResource(R.drawable.frame);
        anim = (AnimationDrawable) dialog_iv_icon.getBackground();
        anim.start();
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.BOTTOM /**| Gravity.TOP*/);
        //lp.x = 100; // 新位置X坐标
        lp.y = 100; // 新位置Y坐标
        lp.width = dp2px(260); // 宽度
        lp.height = dp2px(260); // 高度
        lp.alpha = 0.7f; // 透明度
        dialogWindow.setAttributes(lp);

    }

    private int dp2px(int dpValue) {
        return (int) context.getResources().getDisplayMetrics().density * dpValue;
    }

    public void show() {
        if (dialog != null) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null) {
            anim.stop();
            dialog.dismiss();
            anim = null;
            dialog = null;
        }
    }

}
