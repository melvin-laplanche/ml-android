package la.melvin.mobile.ui.about

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by melvin on 9/17/17.
 */

class AboutPagerAdapter(manager: FragmentManager, items: List<AboutTab>) : FragmentPagerAdapter(manager) {
    private val mTabs = items

    override fun getCount(): Int {
        return mTabs.size
    }

    override fun getItem(position: Int): Fragment? {
        if (position <= mTabs.size) {
            return mTabs[position].instancer.newInstance()
        }
        return null
    }
}