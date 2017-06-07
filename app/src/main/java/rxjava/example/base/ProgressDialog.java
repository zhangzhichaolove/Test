package rxjava.example.base;

import android.app.Dialog;
import android.content.Context;

import rxjava.example.R;

public class ProgressDialog extends Dialog {

    public ProgressDialog(Context context) {
        this(context, R.style.DialogTheme);
    }

    public ProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_loading);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }
}