package la.melvin.mobile.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import la.melvin.mobile.Analytics;
import la.melvin.mobile.App;
import la.melvin.mobile.R;

/**
 * Created by melvin on 3/10/17.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    protected Toolbar mToolbar;

    protected void addActionbar(boolean addHomeButton) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(addHomeButton);
        getSupportActionBar().setHomeButtonEnabled(addHomeButton);
    }

    protected void addActionbar() {
        addActionbar(true);
    }

    // set the title of the Activity (will show in the toolbar)
    public void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    static public void setStatusBarColor(Window window, int colorResource) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(window.getContext(), colorResource));
    }

    // getCallerActivity returns the (parent) activity that explicitly started
    // the current activity
    protected Intent getCallerActivity() {
        String activityName = getIntent().getStringExtra("caller");

        if (activityName != null) {
            try {
                Class cls = Class.forName(activityName);
                Intent intent = new Intent();
                intent.setClass(this, cls);
                return intent;
            } catch (ClassNotFoundException e) {
                Log.e(TAG, "Class '" + activityName + "' not found");
            }
        }

        return super.getParentActivityIntent();
    }

    protected void onResume() {
        super.onResume();
        App.setCurrentActivity(this);
    }

    protected void onPause() {
        clearReferences();
        super.onPause();
    }

    protected void onDestroy() {
        clearReferences();
        Analytics.send();
        super.onDestroy();
    }

    private void clearReferences() {
        Activity currActivity = App.getCurrentActivity();
        if (currActivity != null && currActivity.equals(this))
            App.setCurrentActivity(null);
    }
}