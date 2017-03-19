package la.melvin.mobile.utils;

/**
 * Created by melvin on 3/12/17.
 */

public class Validation {
    private boolean mIsValid = true;
    private String mMessage;
    private int mCode = -1;

    // Creates a failing validation
    public Validation(String message) {
        mIsValid = false;
        mMessage = message;
    }

    // Creates a failing validation
    public Validation(int code, String message) {
        mCode = code;
        mIsValid = false;
        mMessage = message;
    }

    // Creates a passing validation
    public Validation() {
    }

    public boolean isValid() {
        return mIsValid;
    }

    public void setValid(boolean valid) {
        mIsValid = valid;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        mCode = code;
    }
}
