package com.lyphomed.nishantpatel.projectguestlogix.ui.welcome;

import com.lyphomed.nishantpatel.projectguestlogix.base.MvpView;

import io.reactivex.disposables.Disposable;

public interface WelcomeContract {
    interface View extends MvpView {
        void onQuerySubmit(String origin, String destination);

        void onError(String message);

        void onDestinationCodeError();

        void onDestinationCodeCorrect();

        void onOriginCodeError();

        void onOriginCodeCorrect();

        void onDisposables(Disposable d);
    }

    interface Presenter {
        void onUserQuerySubmit(String origin, String destination);
    }
}
