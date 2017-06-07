package rxjava.example.view;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import rxjava.example.R;
import rxjava.example.p.contract.VoiceContract;

/**
 * zzc
 * Created by Administrator on 2017/2/24 0024.
 */

public class VoiceButton extends Button implements AudioManager.AudioStateListener {

    private static final int DISTANCR_Y_CAMNCEL = 50;//取消范围
    private static final int STATE_NORMAL = 1;//正常
    private static final int STATE_RECORDING = 2;//录制
    private static final int STATE_WANT_TO_CANCEL = 3;//取消

    private int mCurState = STATE_NORMAL;

    private AudioManager audioManager;
    private float mTime = 0f;

    //是否已经在录制
    boolean isRecording = false;
    VoiceContract.View view;

    public void setView(VoiceContract.View view) {
        this.view = view;
    }

    public VoiceButton(Context context) {
        this(context, null);
    }

    public VoiceButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoiceButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public interface onFinishListener {
        void onFinis(float time, String filrPath);
    }

    private onFinishListener mListener;

    public void setListener(onFinishListener listener) {
        mListener = listener;
    }

    /**
     * 初始化
     */
    private void init() {
//        setOnLongClickListener(new OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                view.showDialog();
//                isRecording = true;
//                return false;
//            }
//        });
        String dir = Environment.getExternalStorageDirectory() + "/chao";
        audioManager = AudioManager.getInstance(dir);
        audioManager.setAudioStateListener(this);
    }

    boolean iscancel = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                view.showDialog();
                audioManager.prepareAudio();//开始准备
                changeState(STATE_RECORDING);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isRecording) {
                    if (wantToCancel(x, y)) {// 取消时候停止更新音量
                        iscancel = true;
                        changeState(STATE_WANT_TO_CANCEL);
                    } else {
                        iscancel = false;
                        changeState(STATE_RECORDING);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mTime <= 1.5 && mCurState != STATE_WANT_TO_CANCEL) {
                    iscancel = true;
                    view.showShortDialog();
                    handler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1200);//时间过短延迟取消
                    audioManager.cancel();
                    super.onTouchEvent(event);
                } else if (mCurState == STATE_RECORDING) {//正常录制结束
                    view.dismissDialog();
                    audioManager.release();
                    if (mListener != null) {
                        mListener.onFinis(mTime, audioManager.getPath());
                    }
                } else if (mCurState == STATE_WANT_TO_CANCEL) {
                    handler.sendEmptyMessage(MSG_DIALOG_DIMISS);//用户取消
                }
                reset();
                break;
        }
        return true;
    }

    /**
     * 恢复状态
     */
    private void reset() {
        isRecording = false;
        mTime = 0f;
        changeState(STATE_NORMAL);
    }


    /**
     * 根据坐标判断是否取消语音
     *
     * @param x
     * @param y
     * @return
     */
    private boolean wantToCancel(int x, int y) {
        if (x < 0 || x > getWidth()) {
            return true;
        }
        if (y < -DISTANCR_Y_CAMNCEL || y > getHeight() + DISTANCR_Y_CAMNCEL) {
            return true;
        }
        return false;
    }

    /**
     * 更新状态
     *
     * @param state
     */
    private void changeState(int state) {
        if (mCurState != state) {
            mCurState = state;
            switch (state) {
                case STATE_NORMAL://初始状态
                    setBackgroundResource(R.drawable.btn_shape);
                    setText(R.string.azsh);
                    break;
                case STATE_RECORDING://录制
                    setBackgroundResource(R.drawable.btn_shape_skjs);
                    setText(R.string.skjs);
                    if (isRecording) {
                        view.showDialog();
                    }
                    break;
                case STATE_WANT_TO_CANCEL://边缘取消触发
                    setBackgroundResource(R.drawable.btn_shape_skjs);
                    setText(R.string.skqx);
                    view.showCancelDialog();
                    break;
            }
        }
    }

    /**
     * 准备完毕回调
     */
    @Override
    public void wellPrepare() {
        handler.sendEmptyMessage(MSG_AUDIO_PREPARED);
    }

    Thread thread;
    private static final int MSG_AUDIO_PREPARED = 0x88;
    private static final int MSG_VOICE_CHANGED = 0x99;
    private static final int MSG_DIALOG_DIMISS = 0x100;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_AUDIO_PREPARED://准备完成
                    isRecording = true;
                    //view.recording();
                    thread = new Thread(mGetVoiceLevel);
                    thread.start();
                    break;
                case MSG_VOICE_CHANGED://更新音量
                    if (!iscancel)
                        view.levelDialog(audioManager.getVoiceLevel(7));
                    break;
                case MSG_DIALOG_DIMISS://取消录制
                    isRecording = false;
                    thread.interrupt();
                    if (audioManager != null) {
                        audioManager.cancel();
                    }
                    view.dismissDialog();
                    break;
            }
        }
    };

    /**
     * 获取音量大小的线程
     */
    Runnable mGetVoiceLevel = new Runnable() {
        @Override
        public void run() {
            while (isRecording) {
                try {
                    Thread.sleep(200);
                    mTime += 0.2f;
                    handler.sendEmptyMessage(MSG_VOICE_CHANGED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
