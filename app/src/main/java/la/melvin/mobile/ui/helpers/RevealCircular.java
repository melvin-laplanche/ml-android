package la.melvin.mobile.ui.helpers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewAnimationUtils;


public class RevealCircular {
    private final View mView;
    private int mDuration = -1;
    private BasicMotionEvent mMotionEvent;
    private AnimatorListenerAdapter mListener;

    public RevealCircular(View v) {
        mView = v;
    }

    public RevealCircular addDuration(int duration) {
        mDuration = duration;
        return this;
    }

    public RevealCircular addMotionEvent(@Nullable BasicMotionEvent motionEvent) {
        mMotionEvent = motionEvent;
        return this;
    }

    public RevealCircular addListener(AnimatorListenerAdapter listener) {
        mListener = listener;
        return this;
    }

    public void start() {
        // get the center for the clipping circle
        int cx = 0;
        int cy = 0;

        if (mMotionEvent == null) {
            cx = mView.getWidth() / 2;
            cy = mView.getHeight() / 2;
        } else {
            cx = Math.round(mMotionEvent.getRawX());
            cy = Math.round(mMotionEvent.getRawY());
        }

        // get the final radius for the clipping circle
        int finalRadius = Math.max(mView.getWidth(), mView.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(mView, cx, cy, 0, finalRadius);

        if (mListener != null) {
            anim.addListener(mListener);
        }

        if (mDuration > -1) {
            anim.setDuration(mDuration);
        }

        // make the view visible and start the animation
        mView.setVisibility(View.VISIBLE);
        anim.start();

    }
}
