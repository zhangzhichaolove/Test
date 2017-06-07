package rxjava.example.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态权限申请帮助工具类
 * Created by Chao on 2017/3/31.
 */

public class PermissionHelp {

    private static List<String> permissionList = new ArrayList<>();
    private static Activity mActivity;
    private static Dialog dialog;
    private static boolean mImportant;
    private static int PERMISSION_CODE = 18;
    private static int SETTINGS_CODE = 19;

    /**
     * 发送权限申请
     *
     * @param activity  activity
     * @param result    申请回调
     * @param important 是否重要权限
     * @param per       需要申请的权限
     */
    public static void sendApply(Activity activity, PermissionsResult result,
                                 boolean important, String... per) {
        if (result != null) {
            permissionsListener = result;
        }
        mActivity = activity;
        mImportant = important;
        if (!isPermission(activity, per)) {//有权限未通过
            showDialogTipUserRequestPermission(important ? "App运行，需要一些必要的权限，接下来你需要授予这些权限。"
                    : "App运行，需要一些权限，接下来你需要授予这些权限。", per);
        } else {
            if (permissionsListener != null)
                permissionsListener.permissionsResult(true);
        }
    }

    /**
     * 判断权限
     *
     * @param permission
     * @return 只要有一个权限被禁止，即返回false
     */
    public static boolean isPermission(Activity activity, String... permission) {
        for (int i = 0; i < permission.length; i++) {
            if (ContextCompat.checkSelfPermission(activity, permission[i]) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    // 提示用户该请求权限的弹出框
    @SuppressWarnings("deprecation")
    private static void showDialogTipUserRequestPermission(String message, final String... per) {
        // 检查是否有权限未申请。
        if (!isPermission(mActivity, per)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity,
                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("注意：")
                    .setMessage(message);
            if (mImportant) {// 必要权限

                dialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                permissionApply(per);
                            }
                        });
            } else {// 非必要权限
                dialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                permissionApply(per);
                            }
                        }).setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
                ;
            }
            // }
            dialog.setCancelable(!mImportant).show();
        } else {
            if (permissionsListener != null)
                permissionsListener.permissionsResult(true);
        }
    }


    /**
     * 权限申请
     */
    private static void permissionApply(String... per) {
        permissionList.clear();
        if (Build.VERSION.SDK_INT >= 23) {// 6.0以后需要新版权限申请
            for (int i = 0; i < per.length; i++) {// 进入此方法，说明此组一定有还未申请通过的权限，筛选出其中未通过的权限。
                // 权限是否已经 授权 GRANTED---授权 DINIED---拒绝
                if (ContextCompat.checkSelfPermission(mActivity, per[i]) != PackageManager.PERMISSION_GRANTED) {// 判断每一个权限是否都已申请
                    // 0有权限，-1没有权限 Manifest.permission.WRITE_EXTERNAL_STORAGE
                    permissionList.add(per[i]);// 未通过申请的权限列表
                }
            }
            if (permissionList.size() > 0) {// 有未申请权限
                String[] perlist = permissionList.toArray(new String[permissionList.size()]);
                // activity.requestPermissions(perlist, PERMISSION_CODE);
                ActivityCompat.requestPermissions(mActivity, perlist,
                        PERMISSION_CODE);// 请求权限
            } else {
                if (permissionsListener != null)
                    permissionsListener.permissionsResult(true);
            }
        }
    }

    /**
     * 权限申请状态回调
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void onRequestPermissionsResult(int requestCode,
                                                  String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            boolean isMustnot = false;// 是否有不可申请的权限
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {// 移除通过的权限
                    permissionList.remove(permissions[i]);
                } else {// 对未通过的权限进行判定
                    boolean b = mActivity
                            .shouldShowRequestPermissionRationale(permissions[i]);
                    if (!b) {
                        permissionList.remove(permissions[i]);
                        isMustnot = true;
                    }
                }
            }
            if (mImportant) {//重要权限
                if (isMustnot) {// 有点击了不再提醒的必要权限。
                    showDialogTipUserGoToAppSettting();
                    return;
                }
                if (permissionList.size() > 0) {// 有未申请成功的权限
                    //if (permissionsListener != null) {
                    //    permissionsListener.permissionsResult(false);
                    //}
                    String[] perlist = permissionList.toArray(new String[permissionList.size()]);
                    ActivityCompat.requestPermissions(mActivity, perlist,
                            PERMISSION_CODE);// 请求权限
                } else {// 所有权限申请成功
                    if (permissionsListener != null) {
                        permissionsListener.permissionsResult(true);
                    }
                }
            } else {
                if (isMustnot || permissionList.size() > 0) {
                    if (permissionsListener != null) {
                        permissionsListener.permissionsResult(false);
                    }
                } else {
                    if (permissionsListener != null) {
                        permissionsListener.permissionsResult(true);
                    }
                }
            }
        }
    }

    /**
     * 权限设置后回调
     */
    public static void onActivityResult(int requestCode, int resultCode,
                                        Intent data) {
        if (mImportant && requestCode == SETTINGS_CODE) {// 必要权限，缺少权限将不能运行
            String[] perlist = permissionList.toArray(new String[permissionList.size()]);
            if (!isPermission(mActivity, perlist)) {// 必要权限没通过。
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(mActivity, "缺少必要的权限,此功能无法使用。", Toast.LENGTH_SHORT)
                        .show();
                if (permissionsListener != null) {
                    permissionsListener.permissionsResult(false);
                }
            } else {
                if (permissionsListener != null) {
                    permissionsListener.permissionsResult(true);
                }
            }
        }
    }


    /**
     * 提示用户去应用设置界面手动开启权限
     */
    private static void showDialogTipUserGoToAppSettting() {

        dialog = new AlertDialog.Builder(mActivity,
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("注意：")
                .setMessage("由于你拒绝了一些必要权限，需要开启才能继续，现在去应用详情开启？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToAppSetting();
                    }
                })
                .setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private static void goToAppSetting() {
        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
        intent.setData(uri);

        mActivity.startActivityForResult(intent, SETTINGS_CODE);
    }

    private static PermissionsResult permissionsListener;


    public interface PermissionsResult {
        void permissionsResult(boolean Result);
    }


}
