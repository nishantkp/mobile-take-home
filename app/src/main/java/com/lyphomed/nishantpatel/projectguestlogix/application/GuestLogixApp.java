package com.lyphomed.nishantpatel.projectguestlogix.application;

import android.app.Application;

import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.schema.AirlinesDatabase;
import com.lyphomed.nishantpatel.projectguestlogix.data.manager.DataManager;

/**
 * Application class
 */
public class GuestLogixApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Build the dataManager with Database
        DataManager.Build(AirlinesDatabase.getInstance(getApplicationContext()));
    }
}
