package la.melvin.mobile.services.crashreporting

import android.content.Context
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

/**
 * CrashlyticsReporter is a CrashReporting implementation of Fabric's Crashlytics
 */

class CrashlyticsReporter(ctx: Context) : CrashReporting {
    init {
        Fabric.with(ctx, Crashlytics())
    }
}