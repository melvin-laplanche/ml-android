package la.melvin.mobile.api;

import android.content.Context;

import javax.inject.Inject;

import la.melvin.mobile.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by melvin on 3/25/17.
 */
public class BasicResponse<T> implements Callback<T> {
    private Context mContext;

    @Inject
    Retrofit mRetrofit;

    public BasicResponse(Context context) {
        mContext = context;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            onError(APIError.parseError(mRetrofit, response));
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
