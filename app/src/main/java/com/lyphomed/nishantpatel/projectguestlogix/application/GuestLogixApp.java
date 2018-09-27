package com.lyphomed.nishantpatel.projectguestlogix.application;

import android.app.Application;
import android.util.Log;

import com.lyphomed.nishantpatel.projectguestlogix.data.local.callbacks.OnTaskCompletion;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.schema.AirlinesDatabase;
import com.lyphomed.nishantpatel.projectguestlogix.data.manager.DataManager;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.ViaRoute;
import com.lyphomed.nishantpatel.projectguestlogix.utils.TableDataCreation;


import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
