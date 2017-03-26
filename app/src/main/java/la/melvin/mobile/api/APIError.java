package la.melvin.mobile.api;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by melvin on 3/11/17.
 */

public class APIError {
    public static final int INTERNAL_ERROR = -1;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDEN = 403;
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

    public static APIError parseError(Retrofit retrofit, Response<?> response) {
        Converter<ResponseBody, APIError> converter =
                retrofit.responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;
        int status = response.code();

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            error = new APIError();
            error.setMessage("Something went wrong");
            status = 500;
        }

        error.setStatusCode(status);
        return error;
    }

    public int GetStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(int statusCode) {
        this.mStatusCode = statusCode;
    }

    public String GetMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }
}
