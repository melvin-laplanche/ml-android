package la.melvin.mobile.api;

import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;

import la.melvin.mobile.R;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by melvin on 3/11/17.
 */

public class APIError {
    private static final String TAG = APIError.class.getCanonicalName();

    public static final int INTERNAL_ERROR = -1;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int CONFLICT = 409;
    public static final int SERVER_ERROR = 500;

    private int mStatusCode;

    @SerializedName("error")
    private String mMessage;

    public APIError() {
    }

    public APIError(int statusCode, String message) {
        this.mStatusCode = statusCode;
        this.mMessage = message;
    }

    public static APIError parseError(Context ctx, Retrofit retrofit, Throwable t) {
        Exception failedWithException = null;

        // Check if the failure comes from the API
        if (t instanceof HttpException) {
            Converter<ResponseBody, APIError> converter =
                    retrofit.responseBodyConverter(APIError.class, new Annotation[0]);

            Response res = ((HttpException) t).response();

            // Extract the request info
            int status = res.code();
            String url = res.raw().request().url().toString();
            String rawMessage = "Something went wrong";
            String reqID = res.headers().get("X-Request-Id");

            APIError error;
            try {
                error = converter.convert(res.errorBody());
                rawMessage = error.getMessage();
            } catch (IOException e) {
                // For some reason the parsing failed
                error = new APIError();
                error.setMessage("Something went wrong");
                status = 500;
                failedWithException = e;
            }
            error.setStatusCode(status);

            // Log the error
            String logMsg = String.format(Locale.US,
                    "Request failed for %s with code %d and message %s. req_id: %s",
                    url, status, rawMessage, reqID);
            Log.e(TAG, logMsg);

            // if a bug occurred we send it to Crashlytics
            if (failedWithException != null) {
                Crashlytics.log(logMsg);
                Crashlytics.logException(failedWithException);
            }

            return error;
        }

        // internal error, maybe there's no internet connection?
        String msg = ctx.getString(R.string.internal_error);
        return new APIError(APIError.INTERNAL_ERROR, msg);
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(int statusCode) {
        this.mStatusCode = statusCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }
}
