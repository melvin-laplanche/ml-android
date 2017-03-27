package la.melvin.mobile.users;

import io.reactivex.Observable;
import la.melvin.mobile.users.models.Session;
import la.melvin.mobile.users.models.User;
import la.melvin.mobile.users.models.UserCredentials;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by melvin on 3/11/17.
 */

public interface UserApiRequests {
    @POST("/users")
    Observable<User> signUp(
            @Body User user
    );

    @POST("/sessions")
    Observable<Session> signIn(
            @Body UserCredentials creds
    );
}
