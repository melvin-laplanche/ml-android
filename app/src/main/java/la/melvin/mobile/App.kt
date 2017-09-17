package la.melvin.mobile

import android.app.Application
import com.github.salomonbrys.kodein.*
import la.melvin.mobile.services.apiservice.ApiService
import la.melvin.mobile.services.apiservice.RetrofitService
import la.melvin.mobile.services.crashreporting.CrashReporting
import la.melvin.mobile.services.crashreporting.CrashlyticsReporter

/**
 * Created by melvin on 9/16/17.
 */
class App : Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        bind<CrashReporting>() with singleton { CrashlyticsReporter(this@App) }
        bind<ApiService>() with singleton { RetrofitService(this@App.getString(R.string.api_domain)) }
    }
}