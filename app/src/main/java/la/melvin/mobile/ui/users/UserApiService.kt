package la.melvin.mobile.ui.users

import io.reactivex.Observable
import la.melvin.mobile.services.apiservice.ApiService
import retrofit2.http.GET

/**
 * UserApi describes all the api routes to manage users
 */
interface UserApiService {
    @GET("/users/featured")
    fun getFeatured(): Observable<User>

    companion object {
        fun getFeatured(api: ApiService): Observable<User> {
            return api.create(UserApiService::class.java).getFeatured()
        }
    }
}