package la.melvin.mobile

import android.support.v4.app.Fragment

/**
 * FragmentInstancer describes the method needed when implementing a class used to create an
 * instance of a fragment
 */
interface FragmentInstancer {
    fun newInstance(): Fragment
}