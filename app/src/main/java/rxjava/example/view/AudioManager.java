package rxjava.example.view;

import android.media.MediaRecorder;

import java.io.File;
import java.util.UUID;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public class AudioManager {
    private MediaRecorder mediaRecorder;
    private String mDir;
    private String mFilePath;
    private AudioStateListener mlistener;
    private boolean isPrepare = false;

    private static AudioManager mInstance;
    private String fileName;

    private AudioManager(String dir) {
        mDir = dir;
    }

    public static AudioManager getInstance(String dir) {
        if (mInstance == null) {
            synchronized (AudioManager.class) {
                if (mInstance == null) {
                    mInstance = new AudioManager(dir);
                }
            }
        }
        return mInstance;
    }

    public void prepareAudio() {
        try {
            isPrepare = false;
            File dir = new File(mDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = getFileName();
            File file = new File(dir, fileName);
            mFilePath = file.getAbsolutePath();
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setOutputFile(file.getAbsolutePath());
            //设置麦克风为音源
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置音频格式
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            //设置音频编码amr
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();
            if (mlistener != null) {
                mlistener.wellPrepare();
            }
            isPrepare = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getVoiceLevel(int maxLevel) {
        try {
            if (isPrepare) {
                //mediaRecorder.getMaxAmplitude() ==32768
                return maxLevel * mediaRecorder.getMaxAmplitude() / 32768 + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 释放
     */
    public void release() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    /**
     * 取消
     */
    public void cancel() {
        release();
        if (mFilePath != null) {
            File file = new File(mFilePath);
            file.delete();
            mFilePath = null;
        }
    }

    public String getFileName() {

        return UUID.randomUUID().toString() + ".amr";
    }

    public String getPath() {
        return mFilePath;
    }

    /**
     * 准备完毕接口
     */
    public interface AudioStateListener {
        void wellPrepare();
    }

    public void setAudioStateListener(AudioStateListener listener) {
        mlistener = listener;
    }
}
