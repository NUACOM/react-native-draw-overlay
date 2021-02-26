package com.toyberman.drawOverlay;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import static androidx.core.content.ContextCompat.startActivity;

public class RNDrawOverlayModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private final int DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE = 1222;
    private Promise mPromise;
    private final String error  = "Permission was not granted";

    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            super.onActivityResult(activity, requestCode, resultCode, data);
            if (requestCode == DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.canDrawOverlays(activity.getApplicationContext())) {
                        // Permission Granted by Overlay
                        mPromise.resolve(true);
                    }
                    else {
                        mPromise.reject(new Throwable(error));
                    }
                } else {
                    mPromise.resolve(true);
                }

            }
        }


    };


    public RNDrawOverlayModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;

        this.reactContext.addActivityEventListener(mActivityEventListener);
    }

    @Override
    public String getName() {
        return "RNDrawOverlay";
    }


    @ReactMethod
    public void askForDispalayOverOtherAppsPermission(Promise promise) {
        mPromise = promise;
        if (!Settings.canDrawOverlays(this.reactContext)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + this.reactContext.getPackageName()));
            this.reactContext.startActivityForResult(intent, DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE, null);

            final Handler handler = new Handler();
            new Thread(new Runnable() {
                @Override
                @TargetApi(23)
                public void run() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                        return;
                    }
                    Context context = reactContext.getApplicationContext();
                    String packageName = context.getApplicationContext().getPackageName();
                    Intent focusIntent = context.getPackageManager().getLaunchIntentForPackage(packageName).cloneFilter();
                    Activity activity = getCurrentActivity();

                    if (Settings.canDrawOverlays(context)) {
                        focusIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        getReactApplicationContext().startActivity(focusIntent);

                        return;
                    }

                    handler.postDelayed(this, 1000);
                }
            }).start();
        
        } else {
            promise.resolve(true);
        }
    }
}