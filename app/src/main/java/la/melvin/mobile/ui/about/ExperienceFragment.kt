package la.melvin.mobile.ui.about

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.android.KodeinSupportFragment
import com.github.salomonbrys.kodein.instance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_experience.*
import la.melvin.mobile.FragmentInstancer
import la.melvin.mobile.R
import la.melvin.mobile.ui.BaseAdapter

/**
 *
 */
class ExperienceFragment : KodeinSupportFragment(), BaseAdapter.OnClickListener {
    private val TAG: String = ExperienceFragment::class.simpleName!!

    private val aboutApiService: AboutApiService by instance()
    private var disposableResults: Disposable? = null

    private var dataSet: MutableList<Experience> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val adapter = ExperienceAdapter(dataSet, 4)
        // adapter.setOnClickListener(this)

        recycleView.layoutManager = LinearLayoutManager(context)
        recycleView.adapter = adapter

        // fetch the user's contact info
        disposableResults = aboutApiService.getAllExperience()
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe(
                        { results -> onNewResults(results) },
                        { err -> print(err.message) }
                )
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // We make sure to dispose the subscriptions in case the view gets destroyed before the
        // queries finished
        disposableResults?.isDisposed.let { isDisposed ->
            if (isDisposed == false) {
                disposableResults?.dispose()
            }
        }
    }

    // onNewResults adds a new set of results to the data set
    private fun onNewResults(search: ExperienceSearchResults) {
        val startAt = dataSet.size
        val endAt = search.results.size - 1

        dataSet.addAll(search.results)

        // if start is set to 0 we cannot call notifyItemRangeInserted because the adapter might
        // be containing placeholders which need to be removed
        if (startAt == 0) {
            recycleView.adapter.notifyDataSetChanged()
        } else {
            recycleView.adapter.notifyItemRangeInserted(startAt, endAt)
        }
    }

    override fun onItemClick(view: View, position: Int) {

    }

    companion object : FragmentInstancer {
        override fun newInstance(): ExperienceFragment {
            return ExperienceFragment()
        }
    }
}