package la.melvin.mobile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.LazyKodeinAware
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import la.melvin.mobile.services.apiservice.ApiService
import la.melvin.mobile.ui.users.UserApiService


class MainActivity : AppCompatActivity(), LazyKodeinAware {
    override val kodein = LazyKodein(appKodein)
    val apiService: Lazy<ApiService> = kodein.instance<ApiService>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        UserApiService.getFeatured(apiService.value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { res -> Log.i("MainActivity", res.id) },
                        { error -> Log.e("MainActivity", error.message) }
                )
    }
}
