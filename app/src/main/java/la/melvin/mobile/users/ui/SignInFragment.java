package la.melvin.mobile.users.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import la.melvin.mobile.DoneCallback;
import la.melvin.mobile.R;
import la.melvin.mobile.api.APIError;
import la.melvin.mobile.databinding.FragmentSignInBinding;
import la.melvin.mobile.ui.BaseActivity;
import la.melvin.mobile.ui.BaseFragment;
import la.melvin.mobile.ui.helpers.ActivityTransition;
import la.melvin.mobile.ui.helpers.BasicMotionEvent;
import la.melvin.mobile.ui.helpers.HideCircular;
import la.melvin.mobile.ui.helpers.RevealCircular;
import la.melvin.mobile.users.UserApiRequests;
import la.melvin.mobile.users.models.Session;
import la.melvin.mobile.users.models.UserCredentials;
import la.melvin.mobile.utils.Validation;
import retrofit2.Retrofit;


public class SignInFragment extends BaseFragment {
    static public final String TAG = SignInFragment.class.getSimpleName();

    @Inject
    Retrofit mRetrofit;

    // Form Fields
    @BindView(R.id.email)
    EditText mEmail;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.main_layout)
    View mMainLayout;

    // Action buttons
    @BindView(R.id.sign_in_button)
    View mSignInButton;

    // Loader
    @BindView(R.id.loader_view)
    FrameLayout mLoaderView;
    private boolean mIsLoading;
    private BasicMotionEvent mLatestEvent;

    // Observers
    private Disposable mSignInObserver;

    private UserCredentials mCreds = new UserCredentials();

    public SignInFragment() {
        // Required empty public constructor
    }

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSignInBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        View view = binding.getRoot();

        // Let's bind everything
        binding.setCreds(mCreds);
        ButterKnife.bind(this, view);
        getApp().getApiComponent().inject(this);

        setListeners();
        return view;
    }

    protected void setListeners() {
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

        if ((val = mCreds.isPasswordValid(this.getActivity())) != null && !val.isValid()) {
            mPassword.setError(val.getMessage());
            focusView = mPassword;
            cancel = true;
        }

        if ((val = mCreds.isEmailValid(this.getActivity())) != null && !val.isValid()) {
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
            showLoader(new BasicMotionEvent(e), () -> {
                mSignInObserver = mRetrofit.create(UserApiRequests.class)
                        .signIn(mCreds)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((Session sess) -> {
                                    Snackbar.make(mMainLayout, "Worked", Snackbar.LENGTH_LONG).show();
                                },
                                (Throwable t) -> {
                                    APIError err = APIError.parseError(getActivity(), mRetrofit, t);

                                    String errMessage = getString(R.string.sign_in_failed);
                                    int status = err.getStatusCode();
                                    if (status == APIError.INTERNAL_ERROR || status == APIError.SERVER_ERROR) {
                                        errMessage = err.getMessage();
                                    }

                                    Snackbar.make(mMainLayout, errMessage, Snackbar.LENGTH_LONG).show();
                                    hideLoader();
                                });
            });
        }
    }

    @OnClick(R.id.sign_up_button)
    public void signUp(View v) {
        new ActivityTransition(SignUpActivity.class).setSource(getActivity()).start();
    }

    /**
     * Loaders
     */

    public void hideLoader() {
        new HideCircular(mLoaderView).addMotionEvent(mLatestEvent).start();
        mIsLoading = false;
        mLatestEvent = null;
        BaseActivity.setStatusBarColor(getActivity().getWindow(), R.color.colorPrimaryDark);
    }

    public void showLoader(BasicMotionEvent e, final DoneCallback animComplete) {
        mLatestEvent = e;
        mIsLoading = true;
        int color = R.color.action_dark;

        mLoaderView.setBackgroundColor(ContextCompat.getColor(getActivity(), color));
        new RevealCircular(mLoaderView).addMotionEvent(e).addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animComplete.done();
            }
        }).start();
        BaseActivity.setStatusBarColor(getActivity().getWindow(), color);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mSignInObserver != null) {
            mSignInObserver.dispose();
        }
    }
}
