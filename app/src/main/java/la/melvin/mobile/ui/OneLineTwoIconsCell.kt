package la.melvin.mobile.ui

/**
 * Created by melvin on 9/22/17.
 */
data class OneLineTwoIconsCell(val leftIcon: Int?, val label: String, val id: Int? = null, val rightIcon: Int? = null, val isPlaceholder: Boolean = false) {
    companion object {
        fun getPlaceholder(): OneLineTwoIconsCell {
            return OneLineTwoIconsCell(null, "", isPlaceholder = true)
        }
    }
}