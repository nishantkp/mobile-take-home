package com.lyphomed.nishantpatel.projectguestlogix.ui.splashscreen;

import com.lyphomed.nishantpatel.projectguestlogix.base.BasePresenter;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.callbacks.OnTaskCompletion;
import com.lyphomed.nishantpatel.projectguestlogix.data.manager.DataManager;
import com.lyphomed.nishantpatel.projectguestlogix.utils.TableDataCreation;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Splash screen presenter which deals with
 * checking data in database, it's not found populate the database tables from csv files
 */
public class SplashScreenPresenter
        extends BasePresenter<SplashScreenContract.View>
        implements SplashScreenContract.Presenter {

    private DataManager mDataManager;
    private TableDataCreation mTableDataCreation;

    SplashScreenPresenter(DataManager dataManager, TableDataCreation tableDataCreation) {
        mDataManager = dataManager;
        mTableDataCreation = tableDataCreation;
    }

    @Override
    public void checkForData() {
        mDataManager.checkDataAvailability(new OnTaskCompletion() {
            @Override
            public void onTaskComplete() {
                // This means we have data present in the database, so no need to perform insertion
                getView().onDataCheckComplete();
            }

            @Override
            public void onError(String message) {
                Observable<Boolean> airport = mDataManager.fillAirportTable(mTableDataCreation.provideAirportInputStream());
                Observable<Boolean> airlines = mDataManager.fillAirlinesTable(mTableDataCreation.provideAirlineInputStream());
                Observable<Boolean> routes = mDataManager.fillRoutesTable(mTableDataCreation.provideRoutesInputStream());

                Disposable d = Observable.zip(airport, airlines, (t1, t2) -> t1 && t2)
                        .flatMap(val -> routes)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
                getView().onDisposables(d);
            }
        });
    }
}
