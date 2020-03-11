package com.ifenduo.mahattan_x;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.ifenduo.mahattan_x.controller.login.LoginActivity;
import com.ifenduo.mahattan_x.net.OSSManager;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MHXApplication extends Application {
    List<WeakReference> activityList;
    static MHXApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        activityList = new ArrayList<>();
        OSSManager.init(this);
    }

    public static MHXApplication getInstance() {
        return instance;
    }

    public void addActivity(Activity activity) {
        WeakReference<Activity> weakReference = new WeakReference<Activity>(activity);
        activityList.add(weakReference);
    }

    public void finishActivity(Activity activity) {
        if (activityList != null && activityList.size() > 0) {
            activityList.remove(activity);
        }
    }

    public void finishActivities() {
        if (activityList.size() == 0) {
            activityList.clear();
        }
        for (WeakReference<Activity> weakReference : activityList) {
            if (weakReference != null && weakReference.get() != null) {
                weakReference.get().finish();
            }
        }
    }

    public void loginOut(Context context) {
        SharedPreferencesTool.loginOut(this);
        context.startActivity(new Intent(context, LoginActivity.class));
        finishActivities();
    }

}
