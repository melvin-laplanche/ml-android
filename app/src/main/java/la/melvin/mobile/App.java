package la.melvin.mobile;

import android.app.Activity;
import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import la.melvin.mobile.api.ApiComponent;
import la.melvin.mobile.api.ApiModule;
import la.melvin.mobile.api.DaggerApiComponent;

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();

    // No leak here, it's automatically handled in onResume/OnPause/onDestroy
    // of the baseActivity
    private static Activity sCurrentActivity = null;

    private ApiComponent mApiComponent;

    /**
     * This function is sometimes called in background
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Analytics.init(getApplicationContext());

        buildApi();
    }

    private void buildApi() {
        mApiComponent = DaggerApiComponent.builder()
                .apiModule(new ApiModule(getString(R.string.api_domain)))
                .build();
    }

    public ApiComponent getApiComponent() {
        return mApiComponent;
    }

    public static Activity getCurrentActivity() {
        return sCurrentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        sCurrentActivity = currentActivity;
    }
}