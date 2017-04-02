package la.melvin.mobile.api;

import javax.inject.Singleton;

import dagger.Component;
import la.melvin.mobile.users.ui.SignInFragment;

/**
 * Created by melvin on 3/19/17.
 */

@Singleton
@Component(modules = {ApiModule.class})
public interface ApiComponent {
    void inject(SignInFragment fragment);
}
