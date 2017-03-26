package la.melvin.mobile.api;

import javax.inject.Singleton;

import dagger.Component;
import la.melvin.mobile.users.ui.LoginActivity;

/**
 * Created by melvin on 3/19/17.
 */

@Singleton
@Component(modules = {ApiModule.class})
public interface ApiComponent {
    void inject(LoginActivity activity);
}
