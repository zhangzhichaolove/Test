package rxjava.example.p.contract;

import android.hardware.Camera;
import android.view.SurfaceHolder;

import rxjava.example.base.BasePresenter;
import rxjava.example.base.BaseView;

/**
 * Created by Administrator on 2017/3/1 0001.
 */

public interface CameraContract {

    interface View extends BaseView<Presenter> {

        Camera getCamera();

        void startVisible(Camera camera, SurfaceHolder holder);

        void tofFnish();

        void release();
    }

    interface Presenter extends BasePresenter {
        void photograph(Camera camera, SurfaceHolder holder, String path);
    }

}
