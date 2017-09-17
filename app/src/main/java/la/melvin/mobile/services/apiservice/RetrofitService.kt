package la.melvin.mobile.services.apiservice

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by melvin on 9/16/17.
 */
class RetrofitService(mBaseApiUrl: String) : ApiService {
    var mRetrofit: Retrofit


    init {
        val rxCallAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

        var httpLogger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).addInterceptor(httpLogger).build()

        mRetrofit = Retrofit.Builder()
                .baseUrl(mBaseApiUrl)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(rxCallAdapter)
                .build()
    }

    override fun <T> create(service: Class<T>): T {
        return mRetrofit.create(service)
    }
}