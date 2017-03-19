package la.melvin.mobile;

import android.app.Activity;
import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class App extends Application {
    private static String TAG = App.class.getSimpleName();

    // No leak here, it's automatically handled in onResume/OnPause/onDestroy
    // of the baseActivity
    private static Activity sCurrentActivity = null;

    /**
     * This function is sometimes called in background
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Analytics.init(getApplicationContext());
    }

    public static Activity getCurrentActivity() {
        return sCurrentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        sCurrentActivity = currentActivity;
    }
}