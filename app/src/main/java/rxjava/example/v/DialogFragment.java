package rxjava.example.v;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import rxjava.example.R;

/**
 * Created by Chao on 2017/3/13.
 */

public class DialogFragment extends Fragment implements View.OnClickListener {
    BottomSheetBehavior behavior;

    public static DialogFragment newInstance() {
        Bundle args = new Bundle();
        DialogFragment fragment = new DialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_fragment, container, false);
        View bottomSheet = inflate.findViewById(R.id.bottom_sheet);
        LinearLayout ll = (LinearLayout) inflate.findViewById(R.id.ll);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //这里是bottomSheet 状态的改变
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //这里是拖拽中的回调，根据slideOffset可以做一些动画
            }
        });
        for (int i = 0; i < ll.getChildCount(); i++) {
            ll.getChildAt(i).setOnClickListener(this);
        }
        return inflate;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button0:
                if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
            case R.id.button1:
                final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_bottom_sheet, null);
                mBottomSheetDialog.setContentView(view);
                mBottomSheetDialog.show();
                break;
            case R.id.button2:
                new FullSheetDialogFragment().show(getFragmentManager(), "dialog");
                break;
        }
    }
}
