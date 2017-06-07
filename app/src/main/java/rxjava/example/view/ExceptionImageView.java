package rxjava.example.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/3/1 0001.
 */

public class ExceptionImageView extends ImageView {

    public ExceptionImageView(Context context) {
        super(context);
    }

    public ExceptionImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExceptionImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (Exception e) {
            Log.e("ExceptionImageView", "出现异常");
        }
    }
}
