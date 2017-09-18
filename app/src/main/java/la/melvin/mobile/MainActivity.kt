package la.melvin.mobile

import android.os.Bundle
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.github.salomonbrys.kodein.provider
import la.melvin.mobile.services.activityswitcher.ActivitySwitcher
import la.melvin.mobile.ui.about.AboutActivity


class MainActivity : KodeinAppCompatActivity() {
    val activitySwitcher: () -> ActivitySwitcher by provider<ActivitySwitcher>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySwitcher().setAsNewRoot().start(this, AboutActivity::class.java)
    }
}
