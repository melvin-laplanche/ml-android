package la.melvin.mobile.ui.about

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.android.KodeinSupportFragment
import la.melvin.mobile.R
import la.melvin.mobile.FragmentInstancer


/**
 *
 */
class ContactFragment : KodeinSupportFragment() {
    //private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_contact, container, false)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        //mListener = context
    }

    override fun onDetach() {
        super.onDetach()
        //mListener = null
    }

    companion object : FragmentInstancer {
        override fun newInstance(): ContactFragment {
            return ContactFragment()
        }
    }
}
