package rxjava.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import rxjava.example.R;
import rxjava.example.base.BaseActivity;
import rxjava.example.view.ExceptionImageView;

/**
 * 跳转相机拍照并回调显示
 * Created by Administrator on 2017/3/1 0001.
 */

public class ObtainPhotoActivity extends BaseActivity {
    private static final int REQUEST = 168;
    private static final int REQUESTTWO = 188;
    private ExceptionImageView image;
    private String mPath;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_camera_activity);
        mPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        mPath = mPath + "/temp.png";
        image = (ExceptionImageView) findViewById(R.id.image);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri photoUri = Uri.fromFile(new File(mPath));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);//设置照片存储位置
                startActivityForResult(intent, REQUEST);
            }
        });
        findViewById(R.id.start2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ObtainPhotoActivity.this, CameraActivity.class);
                startActivityForResult(intent, REQUESTTWO);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (bitmap != null) {
            image.setImageBitmap(null);
            bitmap.recycle();
            bitmap = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (bitmap != null) {//每次加载前，先释放上次的资源，避免oom
                image.setImageBitmap(null);
                bitmap.recycle();
                bitmap = null;
                System.gc();
            }
            if (data != null) {
                Bundle extras = data.getExtras();
                for (String key : extras.keySet()) {
                    Log.e("Bundle", key);
                    Log.e(key + "=", extras.get(key).toString());
                }
            }
            if (requestCode == REQUEST) {
                //Bundle bundle = data.getExtras();
                //Bitmap bitmap = (Bitmap) bundle.get("data");//因为数据很大，所以系统这里返回的是：略缩图
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(mPath);
                    bitmap = BitmapFactory.decodeStream(fis);
                    image.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == REQUESTTWO) {//自定义相机
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(data.getStringExtra("path"));
                    bitmap = BitmapFactory.decodeStream(fis);
                    Matrix matrix = new Matrix();
                    matrix.setRotate(90);//将图片旋转90度再显示
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    image.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fis.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
