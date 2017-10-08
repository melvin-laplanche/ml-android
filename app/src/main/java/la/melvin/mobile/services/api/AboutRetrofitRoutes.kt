package la.melvin.mobile.services.api

import io.reactivex.Observable
import la.melvin.mobile.ui.about.ExperienceSearchResults
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * UserRetrofitRoutes describes all the api routes to manage users
 */
interface AboutRetrofitRoutes {
    @GET("about/experience")
    fun getAllExperience(@Query("page") page: Int? = null, @Query("per_page") perPage: Int? = null): Observable<ExperienceSearchResults>
}