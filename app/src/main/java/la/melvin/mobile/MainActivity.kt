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

// 98f6531f-d7f6-48bc-b995-02d11ae72062
// 5d400fd0-b459-4941-b970-caed9a64754e