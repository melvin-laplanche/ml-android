package la.melvin.mobile.ui.helpers;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import la.melvin.mobile.App;


public class ActivityTransition {
    private final String TAG = ActivityTransition.class.getSimpleName();

    private Class<?> mTarget;
    private Activity mFrom;
    private boolean mAnimate;
    private Map<String, Object> mData = new HashMap<>();
    private int mRequestCode = -1;
    private boolean mIsNewRoot;
    private boolean mIsReplacingCurrent;

    public ActivityTransition(Class<?> target) {
        mTarget = target;
    }

    public ActivityTransition setSource(Activity from) {
        mFrom = from;
        return this;
    }

    public ActivityTransition setRequestCode(int code) {
        mRequestCode = code;
        return this;
    }

    public ActivityTransition setData(Map<String, Object> data) {
        mData = data;
        return this;
    }

    public ActivityTransition addData(String key, Object data) {
        mData.put(key, data);
        return this;
    }

    public ActivityTransition animate() {
        mAnimate = true;
        return this;
    }

    public ActivityTransition setAsNewRoot() {
        mIsNewRoot = true;
        return this;
    }

    public ActivityTransition replaceCurrentActivity() {
        mIsReplacingCurrent = true;
        return this;
    }

    public void start() {
        if (mFrom == null) {
            mFrom = App.getCurrentActivity();
        }

        Intent intent = new Intent(mFrom, mTarget);
        intent.putExtra("caller", mFrom.getClass().getName());

        for (Map.Entry<String, Object> entry : mData.entrySet()) {
            if (entry.getValue() instanceof Parcelable) {
                intent.putExtra(entry.getKey(), (Parcelable) entry.getValue());
            } else if (entry.getValue() instanceof Serializable) {
                intent.putExtra(entry.getKey(), (Serializable) entry.getValue());
            } else {
                Log.e(TAG, "Value for " + entry.getValue() + " has an unknown type");
            }
        }

        if (mAnimate) {
            startAnimatedActivity(mFrom, intent, mRequestCode);
        } else if (mRequestCode > -1) {
            mFrom.startActivityForResult(intent, mRequestCode);
        } else {
            mFrom.startActivity(intent);
        }

        if (mIsReplacingCurrent) {
            mFrom.finish();
        } else if (mIsNewRoot) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mFrom.overridePendingTransition(0, 0);
            mFrom.finishAffinity();
        }
    }

    private void startAnimatedActivity(Activity activity, Intent intent, int forResult) {
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(activity).toBundle();
        ;

        if (forResult > -1) {
            activity.startActivityForResult(intent, forResult, bundle);
        } else {
            activity.startActivity(intent, bundle);
        }

    }
}
