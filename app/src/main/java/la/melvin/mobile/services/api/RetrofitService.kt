package la.melvin.mobile.services.api

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


/**
 * RetrofitService is a rest client used to query REST APIs
 */
class RetrofitService(mBaseApiUrl: String) {
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

    fun <T> create(service: Class<T>): T {
        return mRetrofit.create(service)
    }
}