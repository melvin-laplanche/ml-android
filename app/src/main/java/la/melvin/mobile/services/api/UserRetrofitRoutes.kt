package la.melvin.mobile.services.api

import io.reactivex.Observable
import la.melvin.mobile.ui.users.User
import retrofit2.http.GET

/**
 * UserRetrofitRoutes describes all the api routes to manage users
 */
interface UserRetrofitRoutes {
    @GET("users/featured")
    fun getFeatured(): Observable<User>
}