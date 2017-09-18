package la.melvin.mobile.ui.about

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.LazyKodeinAware
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import la.melvin.mobile.R
import la.melvin.mobile.ui.users.UserApiService

class AboutActivity : AppCompatActivity(), LazyKodeinAware {
    override val kodein = LazyKodein(appKodein)
    val userApiService: Lazy<UserApiService> = kodein.instance<UserApiService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }
}
