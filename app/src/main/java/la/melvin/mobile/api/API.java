package la.melvin.mobile.api;

import android.content.Context;

import java.io.IOException;
import java.lang.annotation.Annotation;

import la.melvin.mobile.R;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by melvin on 3/11/17.
 */

public class API {
    private static final String BASE_URL = "https://api.github.com/";

    private static Retrofit.Builder sBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static HttpLoggingInterceptor sLogging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static Retrofit sRetrofit = sBuilder.build();
    private static OkHttpClient.Builder sHttpClient = new OkHttpClient.Builder();

    public static <T> T createService(Class<T> serviceClass) {
        if (!sHttpClient.interceptors().contains(sLogging)) {
            sHttpClient.addInterceptor(sLogging);
            sBuilder.client(sHttpClient.build());
            sRetrofit = sBuilder.build();
        }

        return sRetrofit.create(serviceClass);
    }

    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter =
                sRetrofit.responseBodyConverter(APIError.class, new Annotation[0]);

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

    static public class BasicResponse<T> implements Callback<T> {
        private Context mContext;

        public BasicResponse(Context context) {
            mContext = context;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if (response.isSuccessful()) {
                onSuccess(response.body());
            } else {
                onError(API.parseError(response));
            }
            onComplete();
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            if (!call.isCanceled()) {
                int status = APIError.INTERNAL_ERROR;
                String msg = mContext.getString(R.string.internal_error);
                onError(new APIError(status, msg));
            }
            onComplete();
        }

        // onSuccess is called when the request succeed
        public void onSuccess(T body) {
        }

        // onError is called when an error happened
        public void onError(APIError error) {
        }

        // onComplete is always called whatever happened
        public void onComplete() {
        }
    }
}