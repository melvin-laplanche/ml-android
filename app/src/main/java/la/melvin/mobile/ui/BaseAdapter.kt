package la.melvin.mobile.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import la.melvin.mobile.R

/**
 * Created by melvin on 9/24/17.
 */
abstract class BaseAdapter<T>(protected val items: List<T>, protected var totalPlaceholder: Int = 0) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onClickListener: BaseAdapter.OnClickListener? = null
    private var placeholderLayout = R.layout.list_one_line_item_with_two_icons_placeholder

    init {
        if (items.isNotEmpty()) {
            totalPlaceholder = 0
        }
    }

    // Needed to make sure android is not trying to reuse the PlaceholderVH for actual items
    override fun getItemViewType(position: Int): Int {
        return if (isUsingPlaceholder()) -1 else 0
    }

    fun changePlaceHolderLayout(layout: Int) {
        placeholderLayout = layout
    }

    protected fun isUsingPlaceholder(): Boolean {
        return items.isEmpty() && totalPlaceholder != 0
    }

    override fun getItemCount(): Int {
        return if (isUsingPlaceholder()) totalPlaceholder else items.size
    }

    abstract fun onBindHolder(holder: RecyclerView.ViewHolder, item: T)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (!isUsingPlaceholder()) {
            onBindHolder(holder, items[position])
        }
    }

    abstract fun onCreateHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if (isUsingPlaceholder()) {
            val v = LayoutInflater.from(parent?.context).inflate(placeholderLayout, parent, false)
            return PlaceholderVH(v)
        }
        return onCreateHolder(parent, viewType)
    }


    class PlaceholderVH(view: View) : RecyclerView.ViewHolder(view) {}

    interface OnClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int) {}
        fun onRightIconClick(view: View, position: Int) {}
    }
}