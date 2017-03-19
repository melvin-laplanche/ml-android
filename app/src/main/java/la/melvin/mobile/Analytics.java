package la.melvin.mobile;


import android.content.Context;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

// todo singleton
public class Analytics {
    private static String TAG = Analytics.class.getSimpleName();

    private static MixpanelAPI mMixPanel;

    public static void track(Context ctx, int eventName) {
        mMixPanel.track(ctx.getString(eventName));
    }

    public static void send() {
        if (mMixPanel != null) {
            mMixPanel.flush();
        }
    }

    public static void init(Context ctx) {
        mMixPanel = MixpanelAPI.getInstance(ctx, ctx.getResources().getString(R.string.mixpanel_token));
    }
}
