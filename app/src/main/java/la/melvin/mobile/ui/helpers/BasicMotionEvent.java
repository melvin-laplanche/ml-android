package la.melvin.mobile.ui.helpers;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.MotionEvent;

/**
 * Created by melvin on 9/28/15.
 *
 * http://stackoverflow.com/a/3138109/382879
 *
 * The android MotionEvent object is recycled, therefore there is no was to "keep it for later".
 *
 * This class intent to fix this by copying useful data for a MotionEvent object
 */
public class BasicMotionEvent implements Parcelable {
    float mRawX;
    float mRawY;

    public BasicMotionEvent(MotionEvent motionEvent) {
        mRawX = motionEvent.getRawX();
        mRawY = motionEvent.getRawY();
    }

    public BasicMotionEvent(int x, int y) {
        mRawX = x;
        mRawY = y;
    }

    public float getRawY() {
        return mRawY;
    }

    public float getRawX() {
        return mRawX;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.mRawX);
        dest.writeFloat(this.mRawY);
    }

    protected BasicMotionEvent(Parcel in) {
        this.mRawX = in.readFloat();
        this.mRawY = in.readFloat();
    }

    public static final Parcelable.Creator<BasicMotionEvent> CREATOR = new Parcelable.Creator<BasicMotionEvent>() {
        public BasicMotionEvent createFromParcel(Parcel source) {
            return new BasicMotionEvent(source);
        }

        public BasicMotionEvent[] newArray(int size) {
            return new BasicMotionEvent[size];
        }
    };
}
