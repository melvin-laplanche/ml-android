package la.melvin.mobile.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_one_line_item_with_two_icons.view.*
import la.melvin.mobile.R

/**
 * Adapter to render a list of basic cell with one icon on the left, a text, and an icon on the right
 */
class OneLineTwoIconsCellAdapter(items: List<OneLineTwoIconsCell>, totalPlaceholder: Int = 0) : BaseAdapter<OneLineTwoIconsCell>(items, totalPlaceholder) {
    override fun onBindHolder(holder: RecyclerView.ViewHolder, item: OneLineTwoIconsCell) {
        (holder as ViewHolder).bind(item, onClickListener)
    }

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.list_one_line_item_with_two_icons, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(cell: OneLineTwoIconsCell, listener: OnClickListener?) {
            view.leftIcon.visibility = View.INVISIBLE
            view.rightIcon.visibility = View.INVISIBLE
            view.label.text = cell.label
            view.setOnClickListener { view ->
                listener?.onItemClick(view, adapterPosition)
            }

            if (cell.leftIcon != null) {
                view.leftIcon.setImageResource(cell.leftIcon)
                view.leftIcon.visibility = View.VISIBLE
            }

            if (cell.rightIcon != null) {
                view.rightIcon.setImageResource(cell.rightIcon)
                view.rightIcon.visibility = View.VISIBLE

                view.rightIcon.setOnClickListener { view ->
                    listener?.onRightIconClick(view, adapterPosition)
                }
            }
        }
    }
}