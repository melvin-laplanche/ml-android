package la.melvin.mobile.ui;

import android.app.Fragment;

import la.melvin.mobile.App;

public class BaseFragment extends Fragment {
    public App getApp() {
        return ((BaseActivity) this.getActivity()).getApp();
    }
}
