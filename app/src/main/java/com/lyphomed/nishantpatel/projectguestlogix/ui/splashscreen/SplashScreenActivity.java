package com.lyphomed.nishantpatel.projectguestlogix.ui.splashscreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lyphomed.nishantpatel.projectguestlogix.R;
import com.lyphomed.nishantpatel.projectguestlogix.data.manager.DataManager;
import com.lyphomed.nishantpatel.projectguestlogix.ui.welcome.WelcomeActivity;
import com.lyphomed.nishantpatel.projectguestlogix.utils.TableDataCreation;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Splash screen activity which also deals with populating database table on background
 * thread
 */
public class SplashScreenActivity extends AppCompatActivity implements SplashScreenContract.View {

    // Time in milli seconds for splash screen
    private static final int SPLASH_TIME_OUT = 2500;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mCompositeDisposable = new CompositeDisposable();

        SplashScreenPresenter splashScreenPresenter =
                new SplashScreenPresenter(DataManager.getInstance(), new TableDataCreation(this));
        splashScreenPresenter.attachView(this);

        // Check whether data is present in the database ot not
        splashScreenPresenter.checkForData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }

    @Override
    public void onDisposables(Disposable d) {
        mCompositeDisposable.add(d);
    }

    @Override
    public void onDataCheckComplete() {
        // Wait for 2 seconds for splash screen, then start DashboardActivity
        new Handler().postDelayed(() ->
                startActivity(
                        WelcomeActivity.getStarterIntent(SplashScreenActivity.this)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                ), SPLASH_TIME_OUT);
    }
}
