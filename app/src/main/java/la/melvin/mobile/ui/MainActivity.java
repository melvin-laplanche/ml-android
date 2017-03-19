package la.melvin.mobile.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import la.melvin.mobile.R;
import la.melvin.mobile.ui.helpers.ActivityTransition;
import la.melvin.mobile.users.LoginActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addActionbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.global_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.login:
                new ActivityTransition(LoginActivity.class).setSource(this).start();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
