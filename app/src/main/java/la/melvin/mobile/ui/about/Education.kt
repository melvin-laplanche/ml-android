package la.melvin.mobile.ui.about

/**
 * Created by melvin on 9/24/17.
 */
data class Education(
        val id: String,
        val degree: String,
        val gpa: String?,
        val location: String?,
        val description: String?,
        val start_year: Int,
        val end_year: Int?,
        val organization: Organization
)
