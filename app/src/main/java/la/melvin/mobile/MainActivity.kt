package la.melvin.mobile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.LazyKodeinAware
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.provider
import la.melvin.mobile.services.activityswitcher.ActivitySwitcher
import la.melvin.mobile.ui.about.AboutActivity


class MainActivity : AppCompatActivity(), LazyKodeinAware {
    override val kodein = LazyKodein(appKodein)
    val activitySwitcher: Lazy<() -> ActivitySwitcher> = kodein.provider<ActivitySwitcher>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySwitcher.value().setAsNewRoot().start(this, AboutActivity::class.java)
    }
}
