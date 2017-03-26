package la.melvin.mobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import la.melvin.mobile.users.models.UserCredentials;
import la.melvin.mobile.utils.Validation;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UserCredentialsTest {
    @Test
    public void validator_EmailMissing() {
        UserCredentials creds = new UserCredentials();
        Context ctx = InstrumentationRegistry.getTargetContext();

        Validation val = creds.isEmailValid(ctx);
        assertThat(val.getCode(), is(UserCredentials.ERR_EMAIL_MISSING));

        String msg = ctx.getResources().getString(R.string.error_field_required);
        assertThat(val.getMessage(), is(msg));
    }

    @Test
    public void validator_PasswordMissing() {
        UserCredentials creds = new UserCredentials();
        Context ctx = InstrumentationRegistry.getTargetContext();

        Validation val = creds.isPasswordValid(ctx);
        assertThat(val.getCode(), is(UserCredentials.ERR_PASSWORD_INVALID));

        String msg = ctx.getResources().getString(R.string.error_invalid_password);
        assertThat(val.getMessage(), is(msg));
    }

    @Test
    public void validator_EmailWrongFormat() {
        Context ctx = InstrumentationRegistry.getTargetContext();
        UserCredentials creds = new UserCredentials();
        creds.setEmail("invalid email");

        Validation val = creds.isEmailValid(ctx);
        assertThat(val.getCode(), is(UserCredentials.ERR_EMAIL_INVALID));

        String msg = ctx.getResources().getString(R.string.error_invalid_email);
        assertThat(val.getMessage(), is(msg));
    }
}
