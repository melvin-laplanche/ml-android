package la.melvin.mobile.users.models;


import android.content.Context;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import la.melvin.mobile.R;
import la.melvin.mobile.utils.Validation;

/**
 * Created by melvin on 3/12/17.
 */

public class UserCredentials {
    public static final int ERR_EMAIL_MISSING = 0;
    public static final int ERR_EMAIL_INVALID = 1;
    public static final int ERR_PASSWORD_INVALID = 10;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("password")
    private String mPassword;

    public UserCredentials(String email, String password) {
        mEmail = email;
        mPassword = password;
    }

    public UserCredentials() {
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public Validation isEmailValid(Context ctx) {
        if (TextUtils.isEmpty(mEmail)) {
            String msg = ctx.getString(R.string.error_field_required);
            return new Validation(ERR_EMAIL_MISSING, msg);
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            String msg = ctx.getString(R.string.error_invalid_email);
            return new Validation(ERR_EMAIL_INVALID, msg);
        }
        return new Validation();
    }

    public Validation isPasswordValid(Context ctx) {
        if (TextUtils.isEmpty(mPassword)) {
            String msg = ctx.getString(R.string.error_invalid_password);
            return new Validation(ERR_PASSWORD_INVALID, msg);
        }
        return new Validation();
    }
}
