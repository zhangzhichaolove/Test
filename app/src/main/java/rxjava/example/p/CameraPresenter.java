package rxjava.example.p;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import rxjava.example.p.contract.CameraContract;

/**
 * Created by Administrator on 2017/3/1 0001.
 */

public class CameraPresenter implements CameraContract.Presenter {
    private CameraContract.View view;
    private String path;

    public CameraPresenter(CameraContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }


    private Camera.PictureCallback call = new Camera.PictureCallback() {//拍照回调，数据返回
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(path);
                fos.write(bytes);
                view.release();
                view.tofFnish();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void start() {

    }

    @Override
    public void photograph(final Camera mCamera, SurfaceHolder holder, String path) {
        this.path = path;
        Camera.Parameters parameters = mCamera.getParameters();//获取配置
        parameters.setPictureFormat(ImageFormat.JPEG);
        List<Camera.Size> Sizes = parameters
                .getSupportedPictureSizes();// 获取支持保存图片的尺寸
        parameters.setJpegQuality(100); // 设置照片质量
        parameters.setPictureSize(Sizes.get(0).width, Sizes.get(0).height);
        parameters.setFocusMode(Camera.Parameters.FLASH_MODE_AUTO);//自动对焦
        mCamera.setParameters(parameters);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean b, Camera camera) {//自动对焦成功回调
                if (b) {
                    mCamera.takePicture(null, null, call);//拍照
                }
            }
        });
    }
}
