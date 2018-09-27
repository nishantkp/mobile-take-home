package com.lyphomed.nishantpatel.projectguestlogix.ui.splashscreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lyphomed.nishantpatel.projectguestlogix.R;
import com.lyphomed.nishantpatel.projectguestlogix.ui.welcome.WelcomeActivity;

/**
 * Splash screen activity
 */
public class SplashScreenActivity extends AppCompatActivity {

    // Time in milli seconds for splash screen
    private static final int SPLASH_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Wait for 2 seconds for splash screen, then start DashboardActivity
        new Handler().postDelayed(() -> {
            // This method will be executed once the timer is over
            startActivity(
                    WelcomeActivity.getStarterIntent(SplashScreenActivity.this)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
            );
        }, SPLASH_TIME_OUT);
    }
}
