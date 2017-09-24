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
class OneLineTwoIconsCellAdapter(private val items: List<OneLineTwoIconsCell>, totalPlaceholder: Int = 0) : RecyclerView.Adapter<OneLineTwoIconsCellAdapter.ViewHolder>() {
    private val VT_REGULAR = 0
    private val VT_PLACEHOLDER = 1

    private var placeholders: MutableList<OneLineTwoIconsCell> = mutableListOf()
    private var onClickListener: OnClickListener? = null

    init {
        if (items.isEmpty() && totalPlaceholder > 0) {
            for (i in 0.rangeTo(totalPlaceholder)) {
                placeholders.add(OneLineTwoIconsCell.getPlaceholder())
            }
        }
    }

    fun setOnClickListerner(l: OnClickListener) {
        onClickListener = l
    }

    private fun usePlaceholders(): Boolean {
        return items.isEmpty() && placeholders.isNotEmpty()
    }

    override fun getItemViewType(position: Int): Int {
        return if (usePlaceholders()) VT_PLACEHOLDER else VT_REGULAR
    }

    override fun getItemCount(): Int {
        return if (usePlaceholders()) placeholders.size else items.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (!usePlaceholders()) {
            holder?.bind(items[position], onClickListener)
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

    interface OnClickListener {
        fun onItemClick(view: View, position: Int)
        fun onRightIconClick(view: View, position: Int)
    }
}