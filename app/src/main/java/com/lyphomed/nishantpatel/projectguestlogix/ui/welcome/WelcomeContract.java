package com.lyphomed.nishantpatel.projectguestlogix.ui.welcome;

import com.lyphomed.nishantpatel.projectguestlogix.base.MvpView;

public interface WelcomeContract {
    interface View extends MvpView {
        void onQuerySubmit(String origin, String destination);

        void onError(String message);
    }

    interface Presenter {
        void onUserQuerySubmit(String origin, String destination);
    }
}
