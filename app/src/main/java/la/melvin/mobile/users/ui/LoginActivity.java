package la.melvin.mobile.users.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import la.melvin.mobile.DoneCallback;
import la.melvin.mobile.R;
import la.melvin.mobile.api.APIError;
import la.melvin.mobile.api.BasicResponse;
import la.melvin.mobile.databinding.ActivityLoginBinding;
import la.melvin.mobile.ui.BaseActivity;
import la.melvin.mobile.ui.helpers.ActivityTransition;
import la.melvin.mobile.ui.helpers.BasicMotionEvent;
import la.melvin.mobile.ui.helpers.HideCircular;
import la.melvin.mobile.ui.helpers.RevealCircular;
import la.melvin.mobile.users.models.Session;
import la.melvin.mobile.users.models.UserCredentials;
import la.melvin.mobile.utils.Validation;
import retrofit2.Retrofit;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Inject
    Retrofit mRetrofit;

    // Form Fields
    @BindView(R.id.email) EditText mEmail;
    @BindView(R.id.password) EditText mPassword;
    @BindView(R.id.main_layout) View mMainLayout;

    // Action buttons
    @BindView(R.id.sign_in_button) View mSignInButton;

    // Loader
    @BindView(R.id.loader_view) FrameLayout mLoaderView;
    private boolean mIsLoading;
    private BasicMotionEvent mLatestEvent;

    private UserCredentials mCreds = new UserCredentials();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addActionbar();

        bindAndInject();


        mSignInButton.setOnTouchListener((View v, MotionEvent e) -> {
            if (!mIsLoading && e.getAction() == MotionEvent.ACTION_UP) {
                    signIn(v, e);
                }
                return true;
        });

        mPassword.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                signIn(mSignInButton, null);
                return true;
            }
            return false;
        });
    }

    private void bindAndInject() {
        getApp().getApiComponent().inject(this);

        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setCreds(mCreds);

        ButterKnife.bind(this);
    }

    /**
     * Validates the data provided by the user before sending them to the API
     */
    private boolean validateLocal() {
        // Reset errors
        mEmail.setError(null);
        mPassword.setError(null);

        boolean cancel = false;
        View focusView = null;
        Validation val;

        if ((val = mCreds.isPasswordValid(this)) != null && !val.isValid()) {
            mPassword.setError(val.getMessage());
            focusView = mPassword;
            cancel = true;
        }

        if ((val = mCreds.isEmailValid(this)) != null && !val.isValid()) {
            mEmail.setError(val.getMessage());
            focusView = mEmail;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }

        return !cancel;
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void signIn(View view, @Nullable MotionEvent e) {
        if (validateLocal()) {
            showLoader(new BasicMotionEvent(e), new DoneCallback() {
                @Override
                public void done() {
                    mCreds.SignIn(mRetrofit, new BasicResponse<Session>(getBaseContext()) {
                        @Override
                        public void onSuccess(Session body) {
                            Snackbar.make(mMainLayout, "Worked", Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(APIError err) {
                            Snackbar.make(mMainLayout, "Couldn't login with the provided credentials", Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onComplete() {
                            hideLoader();
                        }
                    });
                }
            });
        }

    }

    @OnClick(R.id.sign_up_button)
    public void signUp(View v) {
        new ActivityTransition(SignUpActivity.class).setSource(this).start();
    }

    /**
     * Loaders
     */

    public void hideLoader() {
        new HideCircular(mLoaderView).addMotionEvent(mLatestEvent).start();
        mIsLoading = false;
        mLatestEvent = null;
        setStatusBarColor(getWindow(), R.color.colorPrimaryDark);
    }

    public void showLoader(BasicMotionEvent e, final DoneCallback animComplete) {
        mLatestEvent = e;
        mIsLoading = true;
        int color = R.color.action_dark;

        mLoaderView.setBackgroundColor(ContextCompat.getColor(this, color));
        new RevealCircular(mLoaderView).addMotionEvent(e).addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animComplete.done();
            }
        }).start();
        setStatusBarColor(getWindow(), color);
    }
}

