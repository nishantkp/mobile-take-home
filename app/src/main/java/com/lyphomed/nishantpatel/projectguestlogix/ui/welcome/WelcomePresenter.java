package com.lyphomed.nishantpatel.projectguestlogix.ui.welcome;

import com.lyphomed.nishantpatel.projectguestlogix.base.BasePresenter;
import com.lyphomed.nishantpatel.projectguestlogix.data.manager.DataManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Presenter which validates user query and set callbacks for error or for
 * starting dashboard activity
 */
public class WelcomePresenter extends BasePresenter<WelcomeContract.View>
        implements WelcomeContract.Presenter {

    private DataManager mDataManager;

    WelcomePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public WelcomeContract.View getView() {
        return super.getView();
    }

    @Override
    public void attachView(WelcomeContract.View view) {
        super.attachView(view);
    }

    @Override
    public void onUserQuerySubmit(String origin, String destination) {
        Disposable disposable = mDataManager.provideAirportFromIata3(origin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEvent((airport, error) -> {
                    if (airport == null && error == null) {
                        getView().onOriginCodeError();
                        getView().onError("Provide valid origin code!");
                    } else {
                        getView().onOriginCodeCorrect();
                        mDataManager.provideAirportFromIata3(destination)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnEvent((a, e) -> {
                                    if (a == null && e == null) {
                                        getView().onDestinationCodeError();
                                        getView().onError("Provide valid destination code!");
                                    } else {
                                        getView().onDestinationCodeCorrect();
                                        getView().onQuerySubmit(origin, destination);
                                    }
                                }).subscribe();
                    }
                })
                .subscribe();
        getView().onDisposables(disposable);
    }
}
