package la.melvin.mobile.ui.about

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.android.KodeinSupportFragment
import com.github.salomonbrys.kodein.instance
import kotlinx.android.synthetic.main.fragment_contact.*
import la.melvin.mobile.FragmentInstancer
import la.melvin.mobile.R
import la.melvin.mobile.ui.OneLineTwoIconsCellAdapter
import la.melvin.mobile.ui.users.UserApiService


/**
 *
 */
class ContactFragment : KodeinSupportFragment() {
    val userApiService: UserApiService by instance()

    //private var mListener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val adapter = OneLineTwoIconsCellAdapter(listOf(), 5)

        recycleView.layoutManager = LinearLayoutManager(context)
        recycleView.adapter = adapter
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
