package la.melvin.mobile.ui.users

import io.reactivex.Observable

/**
 * UserApiService describes all the actions that triggers an api request
 */
interface UserApiService {
    fun getFeatured(): Observable<User>
}