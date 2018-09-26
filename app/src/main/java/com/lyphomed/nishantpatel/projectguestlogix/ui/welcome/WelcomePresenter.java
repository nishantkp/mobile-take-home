package com.lyphomed.nishantpatel.projectguestlogix.ui.welcome;

import android.text.TextUtils;

import com.lyphomed.nishantpatel.projectguestlogix.base.BasePresenter;

/**
 * Presenter which validates user query and set callbacks for error or for
 * starting dashboard activity
 */
public class WelcomePresenter extends BasePresenter<WelcomeContract.View>
        implements WelcomeContract.Presenter {

    WelcomePresenter() {
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
        // If user hit search without entering anything or if code is not valid
        // throw an error
        if (origin.length() < 3 && destination.length() < 3) {
            getView().onError("Please provide valid code");
            return;
        }
        getView().onQuerySubmit(origin, destination);
    }
}
