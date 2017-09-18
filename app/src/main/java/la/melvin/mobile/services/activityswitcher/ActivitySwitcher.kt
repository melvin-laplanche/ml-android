package la.melvin.mobile.services.activityswitcher

import android.app.Activity

/**
 * ActivitySwitcher is an interface describing the methods needed to switch between activities
 */
interface ActivitySwitcher {
    fun replaceCurrentActivity(): ActivitySwitcher
    fun setAsNewRoot(): ActivitySwitcher
    fun <T> start(from: Activity, to: Class<T>)

    companion object {
        val INTENT_EXTRA_CALLER = "caller"
    }
}
