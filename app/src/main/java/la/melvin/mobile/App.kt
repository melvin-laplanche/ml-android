package la.melvin.mobile

import android.app.Application
import com.github.salomonbrys.kodein.*
import la.melvin.mobile.services.activityswitcher.ActivitySwitcher
import la.melvin.mobile.services.activityswitcher.ActivityTransition
import la.melvin.mobile.services.api.AboutRetrofitService
import la.melvin.mobile.services.api.RetrofitService
import la.melvin.mobile.services.api.UserRetrofitService
import la.melvin.mobile.services.crashreporting.CrashReporting
import la.melvin.mobile.services.crashreporting.CrashlyticsReporter
import la.melvin.mobile.ui.about.AboutApiService
import la.melvin.mobile.ui.users.UserApiService

/**
 * App is used to define the app dependencies
 */
class App : Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        var apiService = RetrofitService(this@App.getString(R.string.api_domain))

        bind<CrashReporting>() with singleton { CrashlyticsReporter(this@App) }
        bind<UserApiService>() with singleton { UserRetrofitService(apiService) }
        bind<AboutApiService>() with singleton { AboutRetrofitService(apiService) }
        bind<ActivitySwitcher>() with provider { ActivityTransition() }
    }
}