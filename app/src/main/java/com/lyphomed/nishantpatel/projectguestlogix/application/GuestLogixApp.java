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
        DataManager dataManager = DataManager.getInstance();

        TableDataCreation dataCreation = new TableDataCreation(getApplicationContext());
        dataManager.checkDataAvailability(new OnTaskCompletion() {
            @Override
            public void onTaskComplete() {
                // This means we have data present in the database, so no need to perform insertion
            }

            @Override
            public void onError(String message) {
                // This will trigger with we do not have eny data in Database
                // Fill airlines table
                dataManager.fillAirlinesTable(dataCreation.provideAirlineInputStream())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();

                // Fill airports table
                dataManager.fillAirportTable(dataCreation.provideAirportInputStream())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();

                // Fill routes table
                dataManager.fillRoutesTable(dataCreation.provideRoutesInputStream())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
            }
        });
    }
}
