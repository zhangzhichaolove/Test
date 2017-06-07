package rxjava.example.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import rxjava.example.R;
import rxjava.example.base.BaseActivity;

@RuntimePermissions
public class PermissionActivity extends BaseActivity {
    private Button callPhone;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        callPhone = (Button) findViewById(R.id.show_phone);
        callPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionActivityPermissionsDispatcher.showToastWithCheck(PermissionActivity.this);
            }
        });
        dialog = new Dialog(this);
        ProgressBar bar = new ProgressBar(this);
        bar.setPadding(50, 50, 50, 50);
        dialog.setContentView(bar);
        dialog.show();
        Display display = PermissionActivity.this.getWindowManager()
                .getDefaultDisplay();
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        //lp.width = WindowManager.LayoutParams.WRAP_CONTENT; // 宽度
        lp.width = (int) (display.getWidth() / 2.5);
        lp.height = (int) (display.getWidth() / 2.5);
        //lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
        lp.alpha = 0.7f; // 透明度
        dialog.setCancelable(true);
        dialogWindow.setAttributes(lp);
        //getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        callPhone.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 800);

    }

    //在需要获取权限的地方注释
    //@NeedsPermission(Manifest.permission.CALL_PHONE)
    @NeedsPermission({Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void showToast() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:13594347817"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "你没有权限", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(intent);
        Toast.makeText(this, "你已经拥有权限", Toast.LENGTH_SHORT).show();

    }

    //提示用户为什么需要此权限
    @OnShowRationale({Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void showWhy(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("为了能使用完善的功能，你需要为应用授权。")
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//再次执行请求
                    }
                })
                .show();
    }

    //一旦用户拒绝了
    @OnPermissionDenied({Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void denied() {
        Toast.makeText(this, "没有相应的权限，无法使用此功能", Toast.LENGTH_SHORT).show();
    }

    //用户选择的不再询问
    @OnNeverAskAgain({Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void notAsk() {
        Toast.makeText(this, "你已经禁用了权限，现在你需要在设置中为应用授权", Toast.LENGTH_SHORT).show();
        // 跳转到当前应用的设置界面
        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
        //startActivityForResult(intent, SETTINGS_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}