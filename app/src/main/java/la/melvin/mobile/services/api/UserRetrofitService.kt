package la.melvin.mobile.services.api

import io.reactivex.Observable

import la.melvin.mobile.ui.users.User
import la.melvin.mobile.ui.users.UserApiService

/**
 * UserRetrofitService implements the UserApiService using retrofit
 */
class UserRetrofitService(api: RetrofitService) : UserApiService {
    val mApi: RetrofitService = api

    override fun getFeatured(): Observable<User> {
        return mApi.create(UserRetrofitRoutes::class.java).getFeatured()
    }
}