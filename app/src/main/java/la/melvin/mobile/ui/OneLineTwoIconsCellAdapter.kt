package la.melvin.mobile.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_one_line_item_with_two_icons.view.*
import la.melvin.mobile.R

/**
 * Adapter to render a list of basic cell with one icon on the left, a text, and an icon on the right
 * A null content for a cell will display a placeholder
 */
class OneLineTwoIconsCellAdapter(cells: List<OneLineTwoIconsCell>, totalPlaceholder: Int = 0) : RecyclerView.Adapter<OneLineTwoIconsCellAdapter.ViewHolder>() {
    private val VT_REGULAR = 0
    private val VT_PLACEHOLDER = 1

    private val items: List<OneLineTwoIconsCell>
    private var hasRealData = true

    init {
        if (cells.isEmpty() && totalPlaceholder > 0) {
            var mutableItems = mutableListOf<OneLineTwoIconsCell>()
            for (i in 1.rangeTo(totalPlaceholder)) {
                mutableItems.add(OneLineTwoIconsCell.getPlaceholder())
            }
            items = mutableItems
            hasRealData = false
        } else {
            items = cells
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasRealData) VT_REGULAR else VT_PLACEHOLDER
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (hasRealData) {
            holder?.bind(items[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layout = when (viewType) {
            VT_PLACEHOLDER -> R.layout.list_one_line_item_with_two_icons_placeholder
            else -> {
                R.layout.list_one_line_item_with_two_icons
            }
        }

        val v = LayoutInflater.from(parent?.context).inflate(layout, parent, false)
        return ViewHolder(v)
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(cell: OneLineTwoIconsCell) {
            view.leftIcon.visibility = View.INVISIBLE
            view.rightIcon.visibility = View.INVISIBLE

            view.label.text = cell.label

            if (cell.leftIcon != null) {
                view.leftIcon.setImageResource(cell.leftIcon)
                view.leftIcon.visibility = View.VISIBLE
            }

            if (cell.rightIcon != null) {
                view.rightIcon.setImageResource(cell.rightIcon)
                view.rightIcon.visibility = View.VISIBLE
            }
        }
    }
}