package la.melvin.mobile.ui.about

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.github.salomonbrys.kodein.instance
import kotlinx.android.synthetic.main.activity_about.*
import la.melvin.mobile.R
import la.melvin.mobile.ui.users.UserApiService

class AboutActivity : KodeinAppCompatActivity() {
    val userApiService: UserApiService by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // list all the tabs in the order the will be displayed
        val tabs = listOf(
                AboutTab(ContactFragment, R.drawable.ic_person),
                AboutTab(ContactFragment, R.drawable.ic_business_center),
                AboutTab(ContactFragment, R.drawable.ic_school))


        // Create an adapter and link the pager and the tabLayout together
        val adapter = AboutPagerAdapter(supportFragmentManager, tabs)
        pager.adapter = adapter
        tabLayout.setupWithViewPager(pager)

        // Set theab icon and the icon color of each tab
        for (i in 0 until tabLayout.tabCount) {
            val tab = tabLayout.getTabAt(i)

            if (tab != null) {
                tab.setIcon(tabs[i].icon)

                val iconColor = ContextCompat.getColor(this, R.color.colorTextPrimary)
                tab.icon?.setColorFilter(iconColor, PorterDuff.Mode.SRC_IN)
            }
        }
    }
}
