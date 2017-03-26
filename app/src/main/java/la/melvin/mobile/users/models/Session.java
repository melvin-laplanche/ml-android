package la.melvin.mobile.users.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by melvin on 3/11/17.
 */

public class Session {
    @SerializedName("user_id")
    private String mUserID;

    @SerializedName("token")
    private String mToken;

    public String getUserID() {
        return mUserID;
    }

    public void setUserID(String userID) {
        mUserID = userID;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }
}
