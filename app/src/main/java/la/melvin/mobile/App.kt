package la.melvin.mobile

import android.app.Application
import la.melvin.mobile.services.crashreporting.CrashlyticsReporter

/**
 * Created by melvin on 9/16/17.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        CrashlyticsReporter(this)
    }
}