package la.melvin.mobile.users;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by melvin on 3/11/17.
 */

public interface UserEndpoints {
    @POST("/users")
    Call<User> signUp(
            @Body User user
    );

    @POST("/sessions")
    Call<Session> signIn(
            @Body UserCredentials creds
    );
}
