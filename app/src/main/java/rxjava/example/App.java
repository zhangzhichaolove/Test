package rxjava.example;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class App extends Application {
    private static App instance;
    private Set<Activity> allActivities;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化置入一个不设防的VmPolicy
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//TODO Android N 会抛出FileUriExposure  URI暴露异常
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }


    public void registerActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<Activity>();
        }
        allActivities.add(act);
    }

    public void unregisterActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
            act = null;
        }
    }

}
