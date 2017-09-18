package la.melvin.mobile.services.activityswitcher

import android.app.Activity
import android.content.Intent

/**
 * ActivityTransition is a simple class implementing the ActivitySwitcher interface
 */
class ActivityTransition : ActivitySwitcher {
    private var isNewRoot = false
    private var isReplacingCurrent = false

    override fun replaceCurrentActivity(): ActivitySwitcher {
        isReplacingCurrent = true
        return this
    }

    override fun setAsNewRoot(): ActivitySwitcher {
        isNewRoot = true
        return this
    }

    override fun <T> start(from: Activity, to: Class<T>) {
        var intent = Intent(from, to)
        intent.putExtra(ActivitySwitcher.INTENT_EXTRA_CALLER, from.javaClass.name)
        from.startActivity(intent)

        if (isNewRoot) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            from.overridePendingTransition(0, 0)
            from.finish()
        } else if (isReplacingCurrent) {
            from.finish()
        }
    }
}