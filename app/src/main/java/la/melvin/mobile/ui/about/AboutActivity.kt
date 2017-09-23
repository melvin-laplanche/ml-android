package la.melvin.mobile.ui.about

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.github.salomonbrys.kodein.instance
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_about.*
import la.melvin.mobile.R
import la.melvin.mobile.services.imageloader.GlideApp
import la.melvin.mobile.ui.users.User
import la.melvin.mobile.ui.users.UserApiService

class AboutActivity : KodeinAppCompatActivity() {
    val userApiService: UserApiService by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Fetch the background image
        userApiService.getFeatured()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user -> setUserData(user) },
                        { err -> print(err.message) }
                )

        // list of all the tabs in the order they will be displayed
        val tabs = listOf(
                AboutTab(ContactFragment, R.drawable.ic_person),
                AboutTab(ContactFragment, R.drawable.ic_business_center),
                AboutTab(ContactFragment, R.drawable.ic_school))


        // Create an adapter and link the pager and the tabLayout together
        val adapter = AboutPagerAdapter(supportFragmentManager, tabs)
        pager.adapter = adapter
        tabLayout.setupWithViewPager(pager)

        // Set the icon and the icon color of each tab
        for (i in 0 until tabLayout.tabCount) {
            val tab = tabLayout.getTabAt(i)

            if (tab != null) {
                tab.setIcon(tabs[i].icon)
                this.setTabColor(tab)
            }
        }

        // Add a listener to automatically change the color of a tab
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                this@AboutActivity.setTabColor(tab, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                this@AboutActivity.setTabColor(tab, false)
            }
        })
    }

    // setUserData sets the header's data using a user object
    private fun setUserData(user: User) {
        GlideApp.with(this)
                .load(user.picture)
                .centerCrop()
                .into(profilePicture)
        fullName.text = user.fullName()
    }

    // setTabColor sets the right color of a tab depending if it's active or not
    fun setTabColor(tab: TabLayout.Tab?, selected: Boolean? = null) {
        if (tab != null) {
            val isSelected = selected ?: tab.isSelected

            var alpha = R.integer.active_unfocused_alpha
            var iconColor = R.color.icon_active_unfocused_color

            if (isSelected) {
                alpha = R.integer.active_focused_alpha
                iconColor = R.color.icon_active_focused_color
            }

            tab.icon?.mutate()?.alpha = resources.getInteger(alpha)
            tab.icon?.mutate()?.setColorFilter(ContextCompat.getColor(this, iconColor), PorterDuff.Mode.SRC_IN)
        }
    }
}
