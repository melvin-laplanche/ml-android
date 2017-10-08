package la.melvin.mobile.ui.about

import android.support.v7.widget.RecyclerView
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
class EducationAdapter(items: List<Education>, totalPlaceholder: Int = 0) : BaseAdapter<Education>(items, totalPlaceholder) {
    override fun onBindHolder(holder: RecyclerView.ViewHolder, item: Education) {
        (holder as ViewHolder).bind(item, onClickListener)
    }

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_three_lines_with_left_icon, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Education, listener: OnClickListener?) {
            // String version of the start and end dates
            val from = item.start_year.toString()
            val to = item.end_year?.toString() ?: view.context.getString(R.string.date_present)

            view.line1.text = item.organization.short_name ?: item.organization.name
            view.line2.text = item.degree
            view.line3.text = view.context.getString(R.string.date_from_to_short, from, to)

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