package rxjava.example.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import rxjava.example.R;
import rxjava.example.utils.EmptyUtils;


/**
 * Toolbar
 * <p>
 * 2016/12/19
 *
 * @version 1.0
 */

public class CircleToolbar extends Toolbar implements View.OnClickListener {

    public static final int CLICK_TYPE_LEFT = 1;
    public static final int CLICK_TYPE_RIGHT = 2;

    private TextView tvbRight, tvTitle;
    private ImageButton ibtLeft, ibtRight;

    private Drawable leftDrawable, rightDrawable;
    private String titleText, rightText;

    private OnClickListener clickListener;

    public CircleToolbar(Context context) {
        this(context, null);
    }

    public CircleToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.CircleToolbar, defStyleAttr, 0);
        leftDrawable = tintTypedArray.getDrawable(R.styleable.CircleToolbar_toolbarLeftSrc);
        rightDrawable = tintTypedArray.getDrawable(R.styleable.CircleToolbar_toolbarRightSrc);
        titleText = tintTypedArray.getString(R.styleable.CircleToolbar_toolbarTitle);
        rightText = tintTypedArray.getString(R.styleable.CircleToolbar_toolbarRightText);
        tintTypedArray.recycle();

        initView();
        initListener();
    }

    private void initView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.layout_toolbar, null, false);
        tvTitle = (TextView) contentView.findViewById(R.id.toolbar_tv_title);
        tvbRight = (TextView) contentView.findViewById(R.id.toolbar_tvb_right);
        ibtLeft = (ImageButton) contentView.findViewById(R.id.toolbar_ibt_left);
        ibtRight = (ImageButton) contentView.findViewById(R.id.toolbar_ibt_right);

        addView(contentView);

        if (EmptyUtils.isNotEmpty(leftDrawable)) {
            ibtLeft.setVisibility(VISIBLE);
            ibtLeft.setImageDrawable(leftDrawable);
        }
        if (EmptyUtils.isNotEmpty(rightDrawable)) {
            ibtRight.setVisibility(VISIBLE);
            ibtRight.setImageDrawable(rightDrawable);
        }
        if (!TextUtils.isEmpty(titleText)) {
            tvTitle.setVisibility(VISIBLE);
            tvTitle.setText(titleText);
        }
        if (!TextUtils.isEmpty(rightText)) {
            tvbRight.setVisibility(VISIBLE);
            tvbRight.setText(rightText);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
    }

    private void initListener() {
        ibtLeft.setOnClickListener(this);
        ibtRight.setOnClickListener(this);
        tvbRight.setOnClickListener(this);
    }

    public void setClickListener(OnClickListener onClickListener) {
        clickListener = onClickListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_ibt_left:
                if (EmptyUtils.isNotEmpty(clickListener)) {
                    clickListener.onClick(CLICK_TYPE_LEFT);
                }
                break;
            case R.id.toolbar_ibt_right:
                if (EmptyUtils.isNotEmpty(clickListener)) {
                    clickListener.onClick(CLICK_TYPE_RIGHT);
                }
                break;
            case R.id.toolbar_tvb_right:
                if (EmptyUtils.isNotEmpty(clickListener)) {
                    clickListener.onClick(CLICK_TYPE_RIGHT);
                }
                break;
        }
    }

    public interface OnClickListener {
        void onClick(int clickType);
    }
}
