package com.lyphomed.nishantpatel.projectguestlogix.ui.welcome;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lyphomed.nishantpatel.projectguestlogix.R;
import com.lyphomed.nishantpatel.projectguestlogix.config.PublicKeys;
import com.lyphomed.nishantpatel.projectguestlogix.databinding.ActivityWelcomeBinding;
import com.lyphomed.nishantpatel.projectguestlogix.ui.dashboard.DashboardActivity;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.UserQuery;

/**
 * Welcome activity which deals with getting query from user and launch {@link DashboardActivity}
 */
public class WelcomeActivity extends AppCompatActivity implements WelcomeContract.View {

    private static final String LOG_TAG = WelcomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWelcomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);

        WelcomePresenter welcomePresenter = new WelcomePresenter();
        welcomePresenter.attachView(this);

        binding.setUser(new UserQuery());
        binding.setPresenter(welcomePresenter);
    }

    @Override
    public void onQuerySubmit(String origin, String destination) {
        startActivity(
                DashboardActivity.getStarterIntent(this)
                        .putExtra(PublicKeys.USER_ORIGIN, origin)
                        .putExtra(PublicKeys.USER_DESTINATION, destination)
        );
    }

    @Override
    public void onError(String message) {
        Log.i(LOG_TAG, message);
    }
}
