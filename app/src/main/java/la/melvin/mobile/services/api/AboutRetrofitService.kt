package la.melvin.mobile.services.api

import io.reactivex.Observable
import la.melvin.mobile.ui.about.AboutApiService
import la.melvin.mobile.ui.about.EducationSearchResults
import la.melvin.mobile.ui.about.ExperienceSearchResults

/**
 * UserRetrofitService implements the UserApiService using retrofit
 */
class AboutRetrofitService(api: RetrofitService) : AboutApiService {
    val mApi: RetrofitService = api

    override fun getAllExperience(page: Int?, perPage: Int?): Observable<ExperienceSearchResults> {
        return mApi.create(AboutRetrofitRoutes::class.java).getAllExperience(page, perPage)
    }

    override fun getAllEducation(page: Int?, perPage: Int?): Observable<EducationSearchResults> {
        return mApi.create(AboutRetrofitRoutes::class.java).getAllEducation(page, perPage)
    }
}