package la.melvin.mobile.ui.about

import java.text.SimpleDateFormat
import java.util.*

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
) {
    @Transient
    private var _startDate: Date? = null
    val startDate: Date
        get() {
            if (_startDate == null) {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                _startDate = dateFormat.parse(start_date)
            }
            return _startDate!!
        }

    @Transient
    private var _endDate: Date? = null
    val endDate: Date?
        get() {
            if (end_date == null) {
                return null
            }

            if (_endDate == null) {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                _endDate = dateFormat.parse(end_date)
            }
            return _endDate
        }

    fun getElapsedTime(): List<Long> {
        val end = endDate?.time ?: System.currentTimeMillis()
        var different = end - startDate.time

        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24
        val monthInMilli = daysInMilli * 30
        val yearInMilli = monthInMilli * 12

        val elapsedYears = different / yearInMilli
        different %= yearInMilli
        val elapsedMonths = different / monthInMilli

        return listOf(elapsedYears, elapsedMonths)
    }
}