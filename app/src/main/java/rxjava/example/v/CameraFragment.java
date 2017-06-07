package rxjava.example.v;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;

import rxjava.example.R;
import rxjava.example.p.contract.CameraContract;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Administrator on 2017/3/1 0001.
 */

public class CameraFragment extends Fragment implements CameraContract.View, SurfaceHolder.Callback, View.OnClickListener {
    private CameraContract.Presenter presenter;
    private SurfaceView surface;
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Button pz;
    private String mPath;


    public static CameraFragment newInstance() {
        Bundle args = new Bundle();
        CameraFragment fragment = new CameraFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.camera_fragment, container, false);
        surface = (SurfaceView) view.findViewById(R.id.surface);
        pz = (Button) view.findViewById(R.id.pz);
        pz.setOnClickListener(this);
        mHolder = surface.getHolder();
        mHolder.addCallback(this);
        mPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        mPath = mPath + "/temp.png";
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCamera == null) {
            mCamera = getCamera();
            if (mHolder != null) {
                startVisible(mCamera, mHolder);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        release();
    }

    /**
     * 获取相机
     */
    @Override
    public Camera getCamera() {
        Camera camera;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            camera = null;
            e.printStackTrace();
        }
        return camera;
    }

    /**
     * 开始预览相机内容
     */
    @Override
    public void startVisible(Camera camera, SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);//旋转相机
            camera.startPreview();//开始预览
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tofFnish() {
        Intent intent = new Intent();
        intent.putExtra("path", mPath);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    /**
     * 释放相机资源
     */
    @Override
    public void release() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void setPresenter(CameraContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        startVisible(mCamera, mHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        mCamera.stopPreview();
        startVisible(mCamera, mHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        release();
    }

    @Override
    public void onClick(View view) {
        presenter.photograph(mCamera, mHolder, mPath);
    }
}
