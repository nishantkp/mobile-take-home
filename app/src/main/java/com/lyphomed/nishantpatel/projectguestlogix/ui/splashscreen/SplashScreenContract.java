package com.lyphomed.nishantpatel.projectguestlogix.ui.splashscreen;

import com.lyphomed.nishantpatel.projectguestlogix.base.MvpView;

import io.reactivex.disposables.Disposable;

public interface SplashScreenContract {
    interface View extends MvpView {
        void onDisposables(Disposable d);

        void onDataCheckComplete();
    }

    interface Presenter {
        void checkForData();
    }
}
