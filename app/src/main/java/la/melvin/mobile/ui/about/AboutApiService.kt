package la.melvin.mobile.ui.about

import io.reactivex.Observable

/**
 * UserApiService describes all the actions that triggers an api request
 */
interface AboutApiService {
    fun getAllExperience(page: Int? = null, perPage: Int? = null): Observable<ExperienceSearchResults>
}