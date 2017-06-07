package rxjava.example.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rxjava.example.R;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public class DialogManager {
    Dialog dialog;

    ImageView mIcon;
    ImageView mVoice;
    TextView msgTv;

    Context context;

    public DialogManager(Context context) {
        this.context = context;
    }

    public void showStartDialog() {
        if (dialog == null) {
            dialog = new Dialog(context, R.style.DialogThemes);
            View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_view, null);
            dialog.setContentView(inflate);
            mIcon = (ImageView) dialog.findViewById(R.id.dialog_iv_icon);
            mVoice = (ImageView) dialog.findViewById(R.id.dialog_iv_voice);
            msgTv = (TextView) dialog.findViewById(R.id.dialog_tv);
        }
        mIcon.setVisibility(View.VISIBLE);
        mVoice.setVisibility(View.VISIBLE);
        msgTv.setVisibility(View.VISIBLE);
        mIcon.setBackgroundResource(R.mipmap.recorder);
        mVoice.setBackgroundResource(R.mipmap.v1);
        msgTv.setText(R.string.skfs);
        if (!dialog.isShowing())
            dialog.show();
    }

    public void showCancelDialog() {
        if (dialog != null && dialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            msgTv.setVisibility(View.VISIBLE);
            mIcon.setBackgroundResource(R.mipmap.cancel);
            msgTv.setText(R.string.skqx);
        }
    }

    public void showShortDialog() {
        if (dialog != null && dialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            msgTv.setVisibility(View.VISIBLE);
            mIcon.setBackgroundResource(R.mipmap.voice_to_short);
            msgTv.setText(R.string.lygd);
        }
    }

    public void dimissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
//        dialog = null;
    }

    public void updateVoiceDialog(int level) {
        if (dialog != null && dialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            msgTv.setVisibility(View.VISIBLE);

            int resId = context.getResources().getIdentifier("v" + level, "mipmap", context.getPackageName());
            mIcon.setBackgroundResource(R.mipmap.recorder);
            mVoice.setBackgroundResource(resId);
            msgTv.setText(R.string.skfs);
        }
    }

//    public void recording() {
//        if (dialog != null && dialog.isShowing()) {
//            mIcon.setVisibility(View.VISIBLE);
//            mVoice.setVisibility(View.VISIBLE);
//            msgTv.setVisibility(View.VISIBLE);
//            mIcon.setBackgroundResource(R.mipmap.recorder);
//            mVoice.setBackgroundResource(R.mipmap.v1);
//            msgTv.setText(R.string.skfs);
//        }
//    }


}
