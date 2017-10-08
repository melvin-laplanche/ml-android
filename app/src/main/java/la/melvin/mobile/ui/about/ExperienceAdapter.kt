package la.melvin.mobile.ui.about

import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_three_lines_with_left_icon.view.*
import la.melvin.mobile.R
import la.melvin.mobile.services.imageloader.GlideApp
import la.melvin.mobile.ui.BaseAdapter

/**
 * Adapter to render a list of basic cell with one icon on the left, a text, and an icon on the right
 */
class ExperienceAdapter(items: List<Experience>, totalPlaceholder: Int = 0) : BaseAdapter<Experience>(items, totalPlaceholder) {
    override fun onBindHolder(holder: RecyclerView.ViewHolder, item: Experience) {
        (holder as ViewHolder).bind(item, onClickListener)
    }

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_three_lines_with_left_icon, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Experience, listener: OnClickListener?) {

            // Find the elapsed time (ex: 2 yrs 1 mo)
            val intervals = item.getElapsedTime()
            val elapsedYears = intervals[0]
            val elapsedMonths = intervals[1]
            val elapsedYearsStr = view.context.resources.getQuantityString(R.plurals.year_short, intervals[0].toInt(), intervals[0].toInt())
            val elapsedMonthsStr = view.context.resources.getQuantityString(R.plurals.month_short, intervals[1].toInt(), intervals[1].toInt())
            val elapsedTime = if (elapsedYears > 0 && elapsedMonths > 0) {
                view.context.getString(R.string.elapsed_time, elapsedYearsStr, elapsedMonthsStr)
            } else if (elapsedYears > 0) {
                elapsedYearsStr
            } else {
                elapsedMonthsStr
            }

            // String version of the start and end dates
            val from = DateFormat.format("MMM yyyy", item.startDate)
            val to = if (item.endDate != null) DateFormat.format("MMM yyyy", item.endDate) else view.context.getString(R.string.date_present)

            view.line1.text = item.organization.name
            view.line2.text = item.job_title
            view.line3.text = view.context.getString(R.string.date_from_to, from, to, elapsedTime)

            // Load the logo
            GlideApp.with(view.context)
                    .load(item.organization.logo)
                    .fitCenter()
                    .into(view.logo)

            view.setOnClickListener { view ->
                listener?.onItemClick(view, adapterPosition)
            }
        }
    }
}