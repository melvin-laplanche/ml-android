package la.melvin.mobile.ui.about

/**
 * Created by melvin on 9/24/17.
 */
data class Experience(
        val id: String,
        val job_title: String,
        val location: String?,
        val description: String?,
        val start_date: String,
        val end_date: String?,
        val organization: Organization
)