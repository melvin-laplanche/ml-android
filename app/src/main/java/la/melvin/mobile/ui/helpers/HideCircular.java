package la.melvin.mobile.ui.helpers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;


public class HideCircular {
    private final View mView;
    private int mDuration = -1;
    private BasicMotionEvent mMotionEvent;
    private AnimatorListenerAdapter mListener;

    public HideCircular(View v) {
        mView = v;
    }

    public HideCircular addDuration(int duration) {
        mDuration = duration;
        return this;
    }

    public HideCircular addMotionEvent(BasicMotionEvent motionEvent) {
        mMotionEvent = motionEvent;
        return this;
    }

    public HideCircular addListener(AnimatorListenerAdapter listener) {
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

        // get the initial radius for the clipping circle
        int initialRadius = Math.max(mView.getWidth(), mView.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(mView, cx, cy, initialRadius, 0);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                mView.setVisibility(View.INVISIBLE);
            }
        });

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
