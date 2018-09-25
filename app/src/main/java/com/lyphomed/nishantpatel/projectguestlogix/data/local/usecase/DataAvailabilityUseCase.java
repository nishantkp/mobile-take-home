package com.lyphomed.nishantpatel.projectguestlogix.data.local.usecase;

import com.lyphomed.nishantpatel.projectguestlogix.data.local.callbacks.OnTaskCompletion;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.schema.AirlinesDatabase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Use case to check whether data is already present into the database or not
 */
public class DataAvailabilityUseCase {

    private AirlinesDatabase mDatabase;

    public DataAvailabilityUseCase(AirlinesDatabase database) {
        mDatabase = database;
    }

    /**
     * Call this method to check whether data exists into the database or not
     * If there is data in the database, onTaskComplete() callback will be invoked
     * else onError() callback will invoked
     */
    public void checkDataAvailability(final OnTaskCompletion callback) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Disposable disposable = mDatabase.getAirliesDao().getAllAirlines()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    if (!data.isEmpty()) callback.onTaskComplete();
                    else callback.onError("No data!");

                }, error -> callback.onError(error.getMessage()));
        compositeDisposable.add(disposable);
    }
}
