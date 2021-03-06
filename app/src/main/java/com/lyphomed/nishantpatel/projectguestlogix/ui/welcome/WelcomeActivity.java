package com.lyphomed.nishantpatel.projectguestlogix.ui.welcome;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lyphomed.nishantpatel.projectguestlogix.R;
import com.lyphomed.nishantpatel.projectguestlogix.base.BaseActivity;
import com.lyphomed.nishantpatel.projectguestlogix.config.PublicKeys;
import com.lyphomed.nishantpatel.projectguestlogix.data.manager.DataManager;
import com.lyphomed.nishantpatel.projectguestlogix.databinding.ActivityWelcomeBinding;
import com.lyphomed.nishantpatel.projectguestlogix.ui.dashboard.DashboardActivity;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.UserQuery;

import io.reactivex.disposables.Disposable;

/**
 * Welcome activity which deals with getting query from user and launch {@link DashboardActivity}
 */
public class WelcomeActivity extends BaseActivity implements WelcomeContract.View {

    private static final String LOG_TAG = WelcomeActivity.class.getSimpleName();
    private ActivityWelcomeBinding mBinding;

    /**
     * Call this method to get the starter intent to start {@link WelcomeActivity}
     *
     * @param context context of activity from which intent is originated
     * @return starter intent
     */
    public static Intent getStarterIntent(Context context) {
        return new Intent(context, WelcomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);

        WelcomePresenter welcomePresenter = new WelcomePresenter(DataManager.getInstance());
        welcomePresenter.attachView(this);

        mBinding.setUser(new UserQuery());
        mBinding.setPresenter(welcomePresenter);
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
        mBinding.welcomeErrorText.setVisibility(View.VISIBLE);
        mBinding.welcomeDestinationAlert.setVisibility(View.VISIBLE);
        mBinding.welcomeOriginAlert.setVisibility(View.VISIBLE);
        mBinding.welcomeDestinationCode.setBackground(getDrawable(R.drawable.error_border));
        mBinding.welcomeOriginCode.setBackground(getDrawable(R.drawable.error_border));
        Log.i(LOG_TAG, message);
    }

    @Override
    public void onDestinationCodeError() {
        mBinding.welcomeErrorText.setVisibility(View.VISIBLE);
        mBinding.welcomeDestinationAlert.setVisibility(View.VISIBLE);
        mBinding.welcomeDestinationCode.setBackground(getDrawable(R.drawable.error_border));
    }

    @Override
    public void onDestinationCodeCorrect() {
        mBinding.welcomeErrorText.setVisibility(View.INVISIBLE);
        mBinding.welcomeDestinationAlert.setVisibility(View.GONE);
        mBinding.welcomeDestinationCode.setBackground(getDrawable(R.drawable.border));
    }

    @Override
    public void onOriginCodeError() {
        mBinding.welcomeErrorText.setVisibility(View.VISIBLE);
        mBinding.welcomeOriginAlert.setVisibility(View.VISIBLE);
        mBinding.welcomeOriginCode.setBackground(getDrawable(R.drawable.error_border));
    }

    @Override
    public void onOriginCodeCorrect() {
        mBinding.welcomeErrorText.setVisibility(View.INVISIBLE);
        mBinding.welcomeOriginAlert.setVisibility(View.GONE);
        mBinding.welcomeOriginCode.setBackground(getDrawable(R.drawable.border));
    }

    @Override
    public void onDisposables(Disposable d) {
        baseCompositeDisposable.add(d);
    }
}
